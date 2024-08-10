package com;

import java.util.ArrayList;
import java.util.List;

import abt.ABT;
import ast.AST;
import ast.ASTCharObject;
import ast.ASTCharObjectSequence;
import crdt.RGA;
import crdt.RGACharObject;
import crdt.RGACharObjectSequence;
import crdt.RGAId;
import ttf.TTF;
import ttf.TTFCharObject;
import ttf.TTFObjectSequence;

public class GTAFactory {
	public static List<GTAlgorithm> create(int num, String algName) {
		ArrayList<GTAlgorithm> algs = new ArrayList<>();

		if (algName.equals("AST")) {
			for (int i = num; i > 0; i--) {
				ASTCharObject head = new ASTCharObject();
				ASTCharObject tail = new ASTCharObject();
				algs.add(new AST(i, num, new ASTCharObjectSequence(head, tail)));
			}
		} else if (algName.equals("RGA")) {
			for (int i = num; i > 0; i--) {
				RGACharObject head = new RGACharObject(new RGAId(0, 0, 0), "");
				RGACharObject tail = new RGACharObject(new RGAId(0, 0, 1), "");

				algs.add(new RGA(i, num, new RGACharObjectSequence(head, tail)));
			}
		} else if (algName.equals("TTF")) {
			for (int i = num; i > 0; i--) {
				TTFCharObject head = new TTFCharObject("");
				TTFCharObject tail = new TTFCharObject("");
				algs.add(new TTF(i, num, new TTFObjectSequence(head, tail)));
			}
		} else if (algName.equals("ABT")) {
			for (int i = num; i > 0; i--) {
				algs.add(new ABT(i, num));
			}
		}
		return algs;
	}
}