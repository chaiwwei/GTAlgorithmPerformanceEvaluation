package com;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutomaticTestV2 {
	static int siteNum = 3;
	static Random rand = new Random(10);
	static String S = "abcdefghij1234567890";

	public static void main(String[] args) throws Exception {

		int siteOpNum = Integer.parseInt(args[0]);
		int ratio = Integer.parseInt(args[1]);
		int iniSize = Integer.parseInt(args[2]);
		String algName = args[3];
		String fileNumber = args[4];
		String folderName = args[5];

		Parameters paras = new Parameters(siteOpNum, ratio, iniSize, algName);
		// test();
		System.out.println("Testing " + paras.toString());

		String initialDoc = generate(iniSize);

		List<GTAlgorithm> algs = GTAFactory.create(2, paras.algName);
		List<TextDocument> replicas = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			replicas.add(new TextDocument(initialDoc));
			algs.get(i).initializeInternalState(initialDoc);
		}

		test(folderName, fileNumber, paras, algs, replicas);
		System.out.println("running");

	}

	private static void writeFile(String path, long time) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(path, true));
		writer.println(time);
		writer.flush();
		writer.close();
	}

	public static void log(String folderName, String fileName, long concurrencyTime) {
		try {
			Files.createDirectories(Paths.get(folderName + "//Concurrency//"));
			writeFile(folderName + "//Concurrency//" + fileName, concurrencyTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String folderName, String fileName, long localTime, long remoteTime, int memorySize) {

		try {
			Files.createDirectories(Paths.get(folderName + "//Local//"));
			writeFile(folderName + "//Local//" + fileName, localTime);

			Files.createDirectories(Paths.get(folderName + "//Remote//"));
			writeFile(folderName + "//Remote//" + fileName, remoteTime);

			Files.createDirectories(Paths.get(folderName + "//Memory//"));
			writeFile(folderName + "//Memory//" + fileName, memorySize);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void test(String folderName, String fileNumber, Parameters paras, List<GTAlgorithm> algList,
			List<TextDocument> replicas) {

		long localTime = 0;
		long remoteTime = 0;
		int memorySize = 0;

		/*-----------Site 1 generates local operations------------------*/
		GTAlgorithm gt0 = algList.get(0);
		TextDocument doc0 = replicas.get(0);
		ArrayList<String> site0RemoteOps = new ArrayList<>();

		for (int j = 0; j < paras.siteOpNum; j++) {
			CharOperation sop = generateOperation(paras.ratio, doc0);
			long startTime = System.nanoTime();
			String aop = gt0.LOH(sop);
			localTime = localTime + (System.nanoTime() - startTime) / 1000;
			site0RemoteOps.add(aop);
		}

		/*-----------Site 2 handles remote operations------------------*/
		GTAlgorithm gt1 = algList.get(1);
		TextDocument doc1 = replicas.get(1);
		for (int i = 0; i < paras.siteOpNum; i++) {
			String aop = site0RemoteOps.get(i);
			long startTime = System.nanoTime();
			CharOperation sop = gt1.ROH(aop);
			remoteTime = remoteTime + (System.nanoTime() - startTime) / 1000;
			doc1.execute(sop);
		}

		memorySize = algList.get(0).memorySize();

		log(folderName, fileNumber + "_" + paras.toString() + ".txt", localTime, remoteTime, memorySize);
	}

	public static CharOperation generateOperation(int ratio, TextDocument doc) {
		CharOperation sop = null;

		int chance = rand.nextInt(100);
		if (chance < ratio) {
			int len = doc.getLength();
			int insPos = rand.nextInt(len + 1);
			String str = generate(1);
			doc.insert(insPos, str);
			sop = new CharOperation(OpType.ins, str, insPos);
		} else {
			int len = doc.getLength();
			if (len > 1) {
				int delPos = rand.nextInt(len - 1);
				String str = doc.get(delPos, 1);
				doc.delete(delPos, 1);
				sop = new CharOperation(OpType.del, str, delPos);
			} else {
				int insPos = rand.nextInt(len + 1);
				String str = generate(1);
				doc.insert(insPos, str);
				sop = new CharOperation(OpType.ins, str, insPos);
			}
		}

		return sop;
	}

	public static String generate(int num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(S.charAt(rand.nextInt(20)));
		}
		return sb.toString();
	}
}
