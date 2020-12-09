package miniplc0java.analyser;

import miniplc0java.error.AnalyzeError;
import miniplc0java.error.ErrorCode;
import miniplc0java.util.Pos;

import java.io.PrintStream;
import java.util.HashMap;

public class BlockSymbol {
    public static int nextOffset = 0;
    final private HashMap<String, SymbolEntry> blockSymbolTable = new HashMap<>();

    public void addAllOffset(){//当函数返回值不为void时，对所有偏移加一
        for (String key:blockSymbolTable.keySet()){
            blockSymbolTable.get(key).stackOffset++;
        }
    }

    public int getSize(){ return blockSymbolTable.size();}
    public int getNextVariableOffset() {
        return nextOffset++;
    }
    public void addSymbol(String name, boolean isInitialized, boolean isConstant,Type type, Pos curPos) throws AnalyzeError {
        if (this.blockSymbolTable.get(name) != null) {
            throw new AnalyzeError(ErrorCode.DuplicateDeclaration, curPos);
        } else {
            this.blockSymbolTable.put(name, new SymbolEntry(type, isConstant, isInitialized, getNextVariableOffset()));
        }
    }
    public void initializeSymbol(String name, Pos curPos) throws AnalyzeError {
        var entry = this.blockSymbolTable.get(name);
        if (entry == null) {
            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
        } else {
            entry.setInitialized(true);
        }
    }
    public int getOffset(String name, Pos curPos) throws AnalyzeError {
        var entry = this.blockSymbolTable.get(name);
        if (entry == null) {
            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
        } else {
            return entry.getStackOffset();
        }
    }
    public boolean isConstant(String name, Pos curPos) throws AnalyzeError {
        var entry = this.blockSymbolTable.get(name);
        if (entry == null) {
            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
        } else {
            return entry.isConstant();
        }
    }

    /**
     * 查找符号表中是否有Ident,且不抛异常
     * @param name
     * @return 标识符的栈偏移
     */
    public int getIdent(String name){
        var entry = this.blockSymbolTable.get(name);
        if (entry==null) return -1;
        return entry.getStackOffset();
    }

    public Type getType(String name){
        var entry = this.blockSymbolTable.get(name);
        return entry.type;
    }
    public void setType(String name,Type type){
        var entry = this.blockSymbolTable.get(name);
        entry.type = type;
    }

    public int getLength(String name){
        var entry = this.blockSymbolTable.get(name);
        return entry.len;
    }
    public void setLength(String name,int len){
        var entry = this.blockSymbolTable.get(name);
        entry.len = len;
    }
    public boolean isStr(String name){
        var entry = this.blockSymbolTable.get(name);
        return entry.isStr;
    }
    public String getStr(String name){
        var entry = this.blockSymbolTable.get(name);
        return entry.string;
    }
    public void setStr(String name,String str){
        var entry = this.blockSymbolTable.get(name);
        entry.isStr = true;
        entry.string = str;
    }
    public void output(PrintStream output){
        try{
            for (String key:blockSymbolTable.keySet()) {
                output.printf("%02x%n", isConstant(key,new Pos(-1,-1))?0:1);
                output.printf("%08x%n", getLength(key));
                if(isStr(key)){
                    String str = getStr(key);
                    for (int i=0;i<str.length();i++)
                    output.printf("%02x",(int)str.charAt(i));
                }
                else
                    for (int j=0;j<getLength(key);j++){
                        output.print("00");
                    }
                output.print("\n");
            }
        }catch (Exception e){

        }

    }
    
    
    

//    /**
//     * 获取下一个变量的栈偏移
//     *
//     * @return
//     */
//    private int getNextVariableOffset() {
//        return this.nextOffset++;
//    }

//    /**
//     * 添加一个符号
//     *
//     * @param name          名字
//     * @param isInitialized 是否已赋值
//     * @param isConstant    是否是常量
//     * @param curPos        当前 token 的位置（报错用）
//     * @throws AnalyzeError 如果重复定义了则抛异常
//     */
//    private void addSymbol(String name, boolean isInitialized, boolean isConstant, Pos curPos) throws AnalyzeError {
//        if (this.symbolTable.get(name) != null) {
//            throw new AnalyzeError(ErrorCode.DuplicateDeclaration, curPos);
//        } else {
//            this.symbolTable.put(name, new SymbolEntry(isConstant, isInitialized, getNextVariableOffset()));
//        }
//    }

//    /**
//     * 设置符号为已赋值
//     *
//     * @param name   符号名称
//     * @param curPos 当前位置（报错用）
//     * @throws AnalyzeError 如果未定义则抛异常
//     */
//    private void initializeSymbol(String name, Pos curPos) throws AnalyzeError {
//        var entry = this.symbolTable.get(name);
//        if (entry == null) {
//            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
//        } else {
//            entry.setInitialized(true);
//        }
//    }

//    /**
//     * 获取变量在栈上的偏移
//     *
//     * @param name   符号名
//     * @param curPos 当前位置（报错用）
//     * @return 栈偏移
//     * @throws AnalyzeError
//     */
//    private int getOffset(String name, Pos curPos) throws AnalyzeError {
//        var entry = this.symbolTable.get(name);
//        if (entry == null) {
//            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
//        } else {
//            return entry.getStackOffset();
//        }
//    }

//    /**
//     * 获取变量是否是常量
//     *
//     * @param name   符号名
//     * @param curPos 当前位置（报错用）
//     * @return 是否为常量
//     * @throws AnalyzeError
//     */
//    private boolean isConstant(String name, Pos curPos) throws AnalyzeError {
//        var entry = this.symbolTable.get(name);
//        if (entry == null) {
//            throw new AnalyzeError(ErrorCode.NotDeclared, curPos);
//        } else {
//            return entry.isConstant();
//        }
//    }

}
