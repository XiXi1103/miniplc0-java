package miniplc0java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import miniplc0java.analyser.Analyser;
import miniplc0java.error.CompileError;
import miniplc0java.instruction.Instruction;
import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Token;
import miniplc0java.tokenizer.TokenType;
import miniplc0java.tokenizer.Tokenizer;

import net.sourceforge.argparse4j.*;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class App {
    public static void main(String[] args) throws CompileError {
        var argparse = buildArgparse();
        Namespace result;
        try {
            result = argparse.parseArgs(args);
        } catch (ArgumentParserException e1) {
            argparse.handleError(e1);
            return;
        }

        var inputFileName = result.getString("input");
        var outputFileName = result.getString("output");

        InputStream input;
        if (inputFileName.equals("-")) {
            input = System.in;
        } else {
            try {
                input = new FileInputStream(inputFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Cannot find input file.");
                e.printStackTrace();
                System.exit(0);
                return;
            }
        }

        PrintStream output;
        if (outputFileName.equals("-")) {
            output = System.out;
        } else {
            try {
                output = new PrintStream(new FileOutputStream(outputFileName));
            } catch (FileNotFoundException e) {
                System.err.println("Cannot open output file.");
                e.printStackTrace();
                System.exit(0);
                return;
            }
        }

        Scanner scanner;
        scanner = new Scanner(input);
        var iter = new StringIter(scanner);
        var tokenizer = tokenize(iter);

        if (result.getBoolean("tokenize")) {
            // tokenize
            var tokens = new ArrayList<Token>();
            try {
                while (true) {
                    var token = tokenizer.nextToken();
                    if (token.getTokenType().equals(TokenType.EOF)) {
                        break;
                    }
                    tokens.add(token);
                }
            } catch (Exception e) {
                // 遇到错误不输出，直接退出
                System.err.println(e);
                System.exit(0);
                return;
            }
            for (Token token : tokens) {
                output.println(token.toString());
            }
        } else if (result.getBoolean("analyse")) {
            // analyze
            var analyzer = new Analyser(tokenizer);
            List<Instruction> instructions;
            try {
                instructions = analyzer.analyse();
            } catch (Exception e) {
                // 遇到错误不输出，直接退出
                System.err.println(e);
                System.exit(1);
                return;
            }
            try {
                System.out.println("================");
                System.out.println("72303b3e");
                System.out.println("00000001");
                System.out.println(String.format("%08x", Analyser.globalSymbol.getSize()));
                System.out.println(Analyser.globalSymbol.output());
                System.out.println(Analyser.printFuncOutputs());
                System.out.println("====================");
//                output.write(hexStringToBytes("72303b3e" +
//                        "00000001" +
//
//                        "00000001" +
//
//                        "00" +
//                        "00000008" +
//                        "0000000000000000" +
//
//                        "00000002" +
//
//                        "00000000" +
//                        "00000000" +
//                        "00000000" +
//                        "00000000" +
//                        "00000001" +
//                        "48 " +
//                        "00000001" +
//
//                        "00000000" +
//                        "00000000" +
//                        "00000000" +
//                        "00000000" +
//                        "00000004" +
//                        "01" +
//                        "0000000000000004" +
//                        "544949"));


                output.write(hexStringToBytes("72303b3e"));
                output.write(hexStringToBytes("00000001"));
                output.write(hexStringToBytes(String.format("%08x", Analyser.globalSymbol.getSize())));
                output.write(hexStringToBytes(Analyser.globalSymbol.output()));
                output.write(hexStringToBytes(Analyser.printFuncOutputs()));
//                System.out.println(Analyser.printFuncOutputs());
            } catch (Exception e) {

            }
        } else {
            System.err.println("Please specify either '--analyse' or '--tokenize'.");
            System.exit(3);
        }
    }
//    }public static void main(String[] args) throws CompileError {
//        var argparse = buildArgparse();
//        Namespace result;
//        try {
//            result = argparse.parseArgs(args);
//        } catch (ArgumentParserException e1) {
//            argparse.handleError(e1);
//            return;
//        }
//
//        var inputFileName = result.getString("input");
//        var outputFileName = result.getString("output");
//
//        InputStream input;
//        if (inputFileName.equals("-")) {
//            input = System.in;
//        } else {
//            try {
//                input = new FileInputStream(inputFileName);
//            } catch (FileNotFoundException e) {
//                System.err.println("Cannot find input file.");
//                e.printStackTrace();
//                System.exit(0);
//                return;
//            }
//        }
//
//        PrintStream output;
//        if (outputFileName.equals("-")) {
//            output = System.out;
//        } else {
//            try {
//                output = new PrintStream(new FileOutputStream(outputFileName));
//            } catch (FileNotFoundException e) {
//                System.err.println("Cannot open output file.");
//                e.printStackTrace();
//                System.exit(0);
//                return;
//            }
//        }
//
//        Scanner scanner;
//        scanner = new Scanner(input);
//        var iter = new StringIter(scanner);
//        var tokenizer = tokenize(iter);
//
//        if (result.getBoolean("tokenize")) {
//            // tokenize
//            var tokens = new ArrayList<Token>();
//            try {
//                while (true) {
//                    var token = tokenizer.nextToken();
//                    if (token.getTokenType().equals(TokenType.EOF)) {
//                        break;
//                    }
//                    tokens.add(token);
//                }
//            } catch (Exception e) {
//                // 遇到错误不输出，直接退出
//                System.err.println(e);
//                System.exit(0);
//                return;
//            }
//            for (Token token : tokens) {
//                output.println(token.toString());
//            }
//        } else if (result.getBoolean("analyse")) {
//            // analyze
//            var analyzer = new Analyser(tokenizer);
//            List<Instruction> instructions;
//            try {
//                instructions = analyzer.analyse();
//            } catch (Exception e) {
//                // 遇到错误不输出，直接退出
//                System.err.println(e);
//                System.exit(1);
//                return;
//            }
//            System.out.println("---------------------");
//            try{
//                System.out.write(hexStr2Byte("72303b3e"));
//            }catch (Exception e){
//
//            }
//
//            output.println("72 30 3b 3e");
//            output.println("00 00 00 01");
//            output.printf("%08x%n", Analyser.globalSymbol.getSize());
////            output.println(Integer.toHexString(1));
//            Analyser.globalSymbol.output(output);
//            output.println();
//            Analyser.printFuncOutputs(output);
//        } else {
//            System.err.println("Please specify either '--analyse' or '--tokenize'.");
//            System.exit(3);
//        }
//    }
    public static byte[] hexStringToBytes(String str) {
        str = str.replace(" ","");
        str = str.replace("\n","");
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
    private static ArgumentParser buildArgparse() {
        var builder = ArgumentParsers.newFor("miniplc0-java");
        var parser = builder.build();
        parser.addArgument("-t", "--tokenize").help("Tokenize the input").action(Arguments.storeTrue());
        parser.addArgument("-l", "--analyse").help("Analyze the input").action(Arguments.storeTrue());
        parser.addArgument("-o", "--output").help("Set the output file").required(true).dest("output")
                .action(Arguments.store());
        parser.addArgument("file").required(true).dest("input").action(Arguments.store()).help("Input file");
        return parser;
    }

    private static Tokenizer tokenize(StringIter iter) {
        var tokenizer = new Tokenizer(iter);
        return tokenizer;
    }
}
