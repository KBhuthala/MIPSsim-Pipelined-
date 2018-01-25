/* On my honor, I have neither given nor received unauthorized aid on this assignment */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

class disassembledIns extends MIPSsim{
	private long input;
	private int opCode;
	private String instruction;
	private int insType;
	private int rsReg;
	private int rtReg;
	private int rdReg;
	private int base;
	private int immedte;
	private int immedte2;
	private int addr;
	private long val;
	public disassembledIns(int input, int opCode,String instruction,int insType, int rsReg, int rtReg, int rdReg, int base, int immedte, int immedte2,int addr,int val) {
		this.input = input;
		this.opCode = opCode;
		this.instruction = instruction;
		this.insType = insType;
		this.rsReg = rsReg; this.rtReg = rtReg;this.rdReg = rdReg;
		this.base = base; this.immedte = immedte; this.immedte2 = immedte2; this.addr = addr;
		this.val = val;
	} 	
	public disassembledIns() {
		// TODO Auto-generated stub
		 input = 0;
		 opCode = 0;
		 instruction = "";
		 insType = 0;
		 rsReg = 0;
		 rtReg = 0;
		 rdReg = 0;
		 base = 0;
		 immedte = 0;
		 immedte2 = 0;
		 addr = 0;
		 val = 0;
	}
	public disassembledIns(disassembledIns other) {
		// TODO Auto-generated constructor stub
		this.input = other.input;
		this.opCode = other.opCode;
		this.instruction = other.instruction;
		this.insType = other.insType;
		this.rsReg = other.rsReg; this.rtReg = other.rtReg;this.rdReg = other.rdReg;
		this.base = other.base; this.immedte = other.immedte; this.immedte2 = other.immedte2; this.addr = other.addr;
		this.val = other.val;
		
	}
	public long getInput() {
		return this.input ;
	}
	public long setInput(long inp) {
		return this.input=inp;
	}
	public int getOpCode() {
		return this.opCode;
	}
	public int setOpCode(int newopCode) {
		return this.opCode=newopCode;
	}
	public String getInstruction() {
		return this.instruction;
	}
	public String setInstruction(String newinstruction) {
		return this.instruction=newinstruction;
	}
	public int getInsType() {
		return this.insType;
	}
	public int setInsType(int newinsType) {
		return this.insType=newinsType;
	}
	public int getRsReg() {
		return this.rsReg;
	}
	public int setRsReg(int newrsReg) {
		return this.rsReg=newrsReg;
	}
	public int getRtReg() {
		return this.rtReg;
	}
	public int setRtReg(int newrtReg) {
		return this.rtReg=newrtReg;
	}
	public int getRdReg() {
		return this.rdReg;
	}
	public int setRdReg(int newrdReg) {
		return this.rdReg=newrdReg;
	}
	public int getBase() {
		return this.base;
	}
	public int setBase(int newbase) {
		return this.base=newbase;
	}
	public int getImmedte() {
		return this.immedte;
	}
	public int setImmedte(int newimmedte) {
		return this.immedte=newimmedte;
	}
	public int getImmedte2() {
		return this.immedte2;
	}
	public int setImmedte2(int newimmedte2) {
		return this.immedte2=newimmedte2;
	}
	public int getAddr() {
		return this.addr;
	}
	public int setAddr(int newaddr) {
		return this.addr=newaddr;
	}
	public long getVal() {
		return this.val;
	}
	public long setVal(long mem) {
		return this.val=mem;
	}
}

class PreIssueBuf extends MIPSsim {
	private disassembledIns[] queue= new disassembledIns[4]; 
	private int count;
	//private int index;
	//private disassembledIns in;
	
	public PreIssueBuf(PreIssueBuf another) {
	    this.queue[0] = another.queue[0]; 
	    this.queue[1] = another.queue[1]; 
	    this.queue[2] = another.queue[2]; 
	    this.queue[3] = another.queue[3]; 
	    this.count = another.count;
	  }
	public void pushToqueue(disassembledIns in, int index) {
		queue[index] = in;
	}
	public int getCount() {
		return this.count;
	}
	public int setCount(int newCount) {
		return this.count=newCount;
	}
	public disassembledIns PreIssueGet(int indexa) {
		// TODO Auto-generated constructor stub
		return queue[indexa];
	}
}

class PreALU extends MIPSsim {
	private disassembledIns queue[]= new disassembledIns[2]; 
	private int count;	
	
	public PreALU() {
		// TODO Auto-generated constructor stub
		queue[0] = null;
		queue[1] = null;
		count = 0;
	}
	public PreALU(PreALU another) {
		this.queue[0] = another.queue[0]; 
	    this.queue[1] = another.queue[1]; 
	    this.count = another.count;
	  }
	public disassembledIns PreALUGet(int index) {
		// TODO Auto-generated constructor
		return queue[index];
	}
	public void pushToqueue(disassembledIns in, int index) {
		queue[index] = in;
		
	}
	public int getCount() {
		return this.count;
	}
	public int setCount(int newCount) {
		return this.count=newCount;
	}
}
			
public class MIPSsim {
	private static  int ifRead = 1;
	private static  int ifWrite = 2;
	private static  int addressNo = 64;
	private static  int pgmCtr = 256;
	private static int lastIns =0;
	public static PreIssueBuf preIssueBuf, preIssueBufCurr;
	public static PreALU preALU1q, preALU2q, preALU1qCurr, preALU2qCurr;
	public static disassembledIns preMEMq, postALUBuf,postMEMBuf, preMEMqCurr, postALUBufCurr, postMEMBufCurr, waitingIns, executedIns;	
	public static String instructionNameArray[]={
			"J","JR","BEQ","BLTZ","BGTZ","BREAK","SW","LW","SLL","SRL","SRA","NOP","ADD","SUB","MUL","AND","OR","XOR","NOR","SLT","ADDI","ANDI","ORI","XORI"
		};
	public static int[] current = new int[32];
	public static int[] arrIndex0 = new int[32];
	public static int[] arrIndex1 = new int[32];
	public static int[] arrIndex2 = new int[32];
	public static int[] arrIndex3 = new int[32];
	
	
	public static int calcOpCode(long inp){
		int maskBit=0xfc000000;
		return(int)(inp&maskBit)>>>26;
	}
	
	public static int calcRsReg(long inp){
		int maskBit=0x03e00000;
		return (int)(inp&maskBit)>>>21;
	}

	public static int calcRtReg(long inp){
		int maskBit=0x001f0000;
		return (int)(inp&maskBit)>>>16;
	}

	public static int calcRdReg(long inp){
		int maskBit=0x0000f800;
		return (int)(inp&maskBit)>>>11;
	}

	public static int calcBase(long inp){
		int maskBit=0x000003e0;
		return (int)(inp&maskBit)>>>6;
	}

	public static int calcImmedte(long inp){
		int maskBit=0x0000ffff;
		return (int)(inp&maskBit);
	}

	public static int calcImmedte2(long inp){
		int maskBit=0x03ffffff;
		return (int)(inp&maskBit);
	}

	public static int calcInsType(long inp){ 
		int opCde = calcOpCode(inp);
		switch (opCde){
		case 18: case 19: case 20: case 22: case 23: case 56:
		case 57: case 58: case 59:{
			return 'I';
		}
		case 17: case 21: case 24: 
				case 25: case 26: case 27:
			case 48: case 49: case 50: 
				case 51: case 52: case 53:
			case 54: case 55:{
				return 'R';
			}	
		case 16:{
				return 'J';
			}
		}
		return -1;
	}
	
	public static String getNameInstruction(long inp) {
		int opCode = calcOpCode(inp);
		if(opCode < 28) {
			return instructionNameArray[opCode - 16];
		} else if(opCode > 47) {
			return instructionNameArray[opCode - 36];
		} else {
			return null;
		}
	}

	public static void updateCurrentArray(int queueNo){ 
		int no;
		if(queueNo ==0){
			for(no=0;no<32;no++){
				if(arrIndex0[no]==ifWrite){ 
					current[no]=ifWrite;
				}
			}
		}
		else if(queueNo ==1){
			for(no=0;no<32;no++){
				if(arrIndex1[no]==ifWrite){
					current[no]=ifWrite;
				}
			}
		}
		else if(queueNo ==2){
			for(no=0;no<32;no++){
				if(arrIndex2[no]==ifWrite){
					current[no]=ifWrite;
				}
			}
		}
		else if(queueNo ==3){
			for(no=0;no<32;no++){
				if(arrIndex3[no]==ifWrite){
					current[no]=ifWrite;
				}
			}
		}
	}
	
	public static int checkHazard (int queueNo) {
		int i;
		switch (queueNo){
			case 0:{
				for(i=0;i<32;i++){ 
					if(arrIndex0[i] == ifRead && current[i]==ifWrite){ //RAW
						return 0;
					}
					if(arrIndex0[i]== ifWrite && current[i]==ifWrite){ //WAW
						return 0;
					}
				}
				break;
			}
			case 1:{
				for(i=0;i<32;i++){ 
					if(arrIndex1[i] == ifRead && (arrIndex0[i] == ifWrite || current[i]==ifWrite)){ //RAW
						return 0;
					}
					if(arrIndex1[i] == ifWrite && (arrIndex0[i] == ifWrite || current[i]==ifWrite)){//WAW
						return 0;
					}
					if(arrIndex1[i] == ifWrite && arrIndex0[i] == ifRead){//WAR
						return 0;
					}
				}
				break;
			}
			case 2:{
				for(i=0;i<32;i++){ 
					if(arrIndex2[i] == ifRead  && (arrIndex1[i] == ifWrite || arrIndex0[i] == ifWrite || current[i]==ifWrite)){ //RAW
						return 0;
					}
					if(arrIndex2[i] == ifWrite && (arrIndex1[i] == ifWrite || arrIndex0[i] == ifWrite || current[i]==ifWrite)){//WAW
						return 0;
					}
					if(arrIndex2[i] == ifWrite && (arrIndex1[i] == ifRead || arrIndex0[i] == ifRead)){//WAR
						return 0;
					}
				}
				break;
			}
			case 3:{
				for(i=0;i<32;i++){ 
					if(arrIndex3[i] == ifRead  && (arrIndex2[i] == ifWrite || arrIndex1[i] == ifWrite || arrIndex0[i] == ifWrite || current[i]==ifWrite)){ //RAW
						return 0;
					}
					if(arrIndex3[i] == ifWrite && (arrIndex2[i] == ifWrite || arrIndex1[i] == ifWrite || arrIndex0[i] == ifWrite || current[i]==ifWrite)){//WAW
						return 0;
					}
					if(arrIndex3[i] == ifWrite && (arrIndex2[i] == ifRead || arrIndex1[i] == ifRead || arrIndex0[i] == ifRead)){//WAR
						return 0;
					}
				}
				break;			
			}
		}
		return 1; 
	}
	
	public static int checkLoadStore(int queueNo){
		int i;
		disassembledIns inst;
		int opCode;
		
		if(preIssueBufCurr.PreIssueGet(queueNo) != null){
		inst = preIssueBufCurr.PreIssueGet(queueNo);
		opCode= calcOpCode(inst.getInput());		
		}
		else{		
			return 0;
		}
		
		if(opCode ==22 || opCode ==23){
			for(i=0;i<queueNo;i++){ 
			
					return 0;
			}
			return 1;
		}
		else{
			return 1;
		}
	}
	
	
	public static void flushArray(){
		int i;
		for(i=0;i<32;i++){
			arrIndex0[i]=0;
			arrIndex1[i]=0;
			arrIndex2[i]=0;
			arrIndex3[i]=0;
		}
	}
	
	
	public static disassembledIns disassembledInsCurr(long inp){ 			
		disassembledIns inst; 
		inst = new disassembledIns();
		inst.setInput(inp);
		inst.setOpCode(calcOpCode(inp));
		inst.setInstruction(getNameInstruction(inp));
		int tempinsType = inst.setInsType(calcInsType(inp));
		switch (tempinsType){ 
			case 'J':{
				inst.setImmedte2(calcImmedte2(inp));
				break;
			}
			case 'I':{
				inst.setRsReg(calcRsReg(inp));
				inst.setRtReg(calcRtReg(inp));
				inst.setImmedte(calcImmedte(inp));
				break;
			}
			case 'R':{
				inst.setRsReg(calcRsReg(inp));
				inst.setRtReg(calcRtReg(inp));
				inst.setRdReg(calcRdReg(inp));
				inst.setBase(calcBase(inp));;
				break;
			}
		}
		return inst;
		
	}
		
	
	public static void instLoadArray(disassembledIns ins, int chk[]){
		if(ins.getInsType() == 'R'){
			if(ins.getOpCode() == 24 ||ins.getOpCode() == 25 ||ins.getOpCode() == 26){// SLL,SRL,SRA
				(chk)[ins.getRtReg()]= ifRead;
				(chk)[ins.getRdReg()]= ifWrite;
			}
			else{
				(chk)[ins.getRtReg()]= ifRead;
				(chk)[ins.getRsReg()]= ifRead;
				(chk)[ins.getRdReg()]= ifWrite;
			}
		}
		else if(ins.getInsType() =='I'){
			if(ins.getOpCode() ==18){//BEQ
				(chk)[ins.getRtReg()]= ifRead;
				(chk)[ins.getRsReg()]= ifRead;			
			}
			else if(ins.getOpCode() == 19 || ins.getOpCode() == 20){//BLTZ BGTZ
				(chk)[ins.getRsReg()]= ifRead;
			}
			else if(ins.getOpCode() == 22){ //STORE
				(chk)[ins.getRtReg()]= ifRead;
				(chk)[ins.getRsReg()]= ifRead;
			}
			else{
				(chk)[ins.getRsReg()]= ifRead;
				(chk)[ins.getRtReg()]= ifWrite;
			}
		}
	}
	
	public static void instLoad(disassembledIns inst, int queueNo){ 
		if(queueNo ==0)
			instLoadArray(inst,arrIndex0); 
		else if(queueNo ==1)
			instLoadArray(inst,arrIndex1);
		else if(queueNo ==2)
			instLoadArray(inst,arrIndex2);
		else if(queueNo ==3)
			instLoadArray(inst,arrIndex3);
	}
	
	public static int issueInst() {
		disassembledIns chkLdSt= null;
		disassembledIns chkOther= null;
		int issue_count=0;
		int i;
		flushArray();

		for(i=0;i<4;i++){
			disassembledIns chkIns;
			chkIns = preIssueBufCurr.PreIssueGet(i);
			if(chkIns == null) { 
				break;
			}
			if(chkLdSt!= null && chkOther!= null) {
				break;
			}
			instLoad(chkIns,i);
			if((checkHazard(i) != 0) && (checkLoadStore(i) != 0)){
				if(calcOpCode(chkIns.getInput())== 22 || calcOpCode(chkIns.getInput())==23){
					if(chkLdSt == null){
						chkLdSt=chkIns;
						updateCurrentArray(i);
					}
					else
						continue;
				}
				else{
					if(chkOther == null){
						chkOther=chkIns;
						updateCurrentArray(i);
					}
					else
						continue;
				}
			}
		}

		if(chkLdSt!=null && preALU1qCurr.getCount() <2){
			//simulate.println("I am in loop 503!");
			loadPreALU(chkLdSt,preALU1q);
			remPreIssueBuf(chkLdSt);
			issue_count++;
		}
		if(chkOther!=null && preALU2qCurr.getCount() <2){
			
			//simulate.println("I am in loop 514!");
			//simulate.println(preALU2qCurr.getCount());
			//simulate.println(cand_else);
			loadPreALU(chkOther,preALU2q);
			remPreIssueBuf(chkOther);
			issue_count++;
		}
		return issue_count;	
	}
	
	public static int loadPreALU(disassembledIns inst, PreALU preALUChk){
		if(preALUChk.getCount() == 2){
			return -1;
		}
		else{	
			//simulate.println("in Enq cpre"+preALU2qCurr.getCount());
			//simulate.println("in Enq pre"+preALU2q.getCount());
			preALUChk.pushToqueue(inst, preALUChk.getCount());
			preALUChk.setCount(preALUChk.getCount() + 1);
			//simulate.println("in Enq cpre"+preALU2qCurr.getCount());
			//simulate.println("in Enq pre"+preALU2q.getCount());
			return 1;
		}
	}

	public static int remPreALU (PreALU preALUChk){ 	
		if(preALUChk.PreALUGet(0) == null){
			return -1;
		}
		else{
			preALUChk.pushToqueue(preALUChk.PreALUGet(1),0);
			preALUChk.pushToqueue(null,1);
			preALUChk.setCount(preALUChk.getCount() - 1);
			return 1;
		}
	}
	
	public static int setToNull(disassembledIns inst){
		int i;
		for(i=0;i<4;i++){
			if(preIssueBuf.PreIssueGet(i) == inst){
				preIssueBuf.pushToqueue(null,i);
				return i;
			}
		}

		return -1;
	}
	
	public static int loadPreIssueBuf (disassembledIns inst){ 
		if(preIssueBuf.getCount() ==4){
			return -1;		
		}
		else{
			//simulate.println("at 563"+ins);	
			preIssueBuf.pushToqueue(inst,preIssueBuf.getCount());
			//simulate.println("In preIssueBuf" +preIssueBuf.PreALUGet(preIssueBuf.getCount()));
			//simulate.println("at 570 "+preIssueBuf.getCount());
			preIssueBuf.setCount(preIssueBuf.getCount() + 1);
			//simulate.println("After adding one "+ preIssueBuf.getCount());
			return 1;
		}
	}
	public static int remPreIssueBuf(disassembledIns inst){ 	
			int sub;
			if((sub=setToNull(inst))>=0){
				int i;
				for(i = sub;i<3;i++){
					preIssueBuf.pushToqueue(preIssueBuf.PreIssueGet(i+1), i);
				}
				preIssueBuf.pushToqueue(null,3);
				preIssueBuf.setCount(preIssueBuf.getCount() - 1);
				return 1;
			}
			else{
				return 0;
			}
		}
	
	public static int[] regStore = new int[32]; 
	public static long[] valStore = new long[100];
	
	public static int wbUnitExec(){
		int wbUnitcount=0;
		if(postMEMBufCurr!= null){
			regStore[postMEMBufCurr.getRtReg()] = (int) postMEMBufCurr.getVal();
			current[postMEMBufCurr.getRtReg()]=0;
			wbUnitcount++;
		}

		if(postALUBufCurr != null){
			if(postALUBufCurr.getInsType() == 'R'){
				regStore[postALUBufCurr.getRdReg()] = (int) postALUBufCurr.getVal();
				current[postALUBufCurr.getRdReg()]=0;
				wbUnitcount++;
			}
			else if(postALUBufCurr.getInsType()  == 'I'){
				regStore[postALUBufCurr.getRtReg()] = (int) postALUBufCurr.getVal();
				current[postALUBufCurr.getRtReg()]=0;
				wbUnitcount++;
			}
		}
		return wbUnitcount;
	}
	
	public static int executeALU2(disassembledIns inst){
		int val=0;
		int opCode;
		opCode = calcOpCode(inst.getInput());
		switch (opCode){
		case 59: 
			val =regStore[inst.getRsReg()]^ inst.getImmedte() ;
			break;
			case 25: 
				val = (int)regStore[inst.getRtReg()] >>> inst.getBase();
				break;
			case 53: 
			val =regStore[inst.getRsReg()]^ regStore[inst.getRtReg()];
			break;	
			case 49: 
				val =regStore[inst.getRsReg()]- regStore[inst.getRtReg()];
				break;
			case 56: 
				val= regStore[inst.getRsReg()]+ inst.getImmedte();
				break;
			case 50: 
				val =regStore[inst.getRsReg()]* regStore[inst.getRtReg()];
				break;
			case 26: 
				val = regStore[inst.getRtReg()] >> inst.getBase();
				break;
			case 48: 
				val =regStore[inst.getRsReg()]+ regStore[inst.getRtReg()];
				break;	
			case 52: 
				val =regStore[inst.getRsReg()]| regStore[inst.getRtReg()];
				break;
			case 24: 
				val = (int)regStore[inst.getRtReg()] << inst.getBase();
				break;
			case 54:
				val = ~(regStore[inst.getRsReg()] | regStore[inst.getRtReg()]);
				break;
			case 57: 
				val =regStore[inst.getRsReg()]& inst.getImmedte() ;
				break;
			case 51: 
				val =regStore[inst.getRsReg()]& regStore[inst.getRtReg()];
				break;
			case 55:{ 
				if(regStore[inst.getRsReg()] < regStore[inst.getRtReg()])
					val=1;
				else
					val=0;
				break;
			}
			case 58: 
				val =regStore[inst.getRsReg()]| inst.getImmedte() ;
				break;
		
			default:
				return -1;
		}
		
		return val;	
	}
	
	public static int execBranchInst(disassembledIns branch){
		flushArray();
		int i;
		for(i=0;i<preIssueBufCurr.getCount();i++){
			instLoad(preIssueBufCurr.PreIssueGet(i),i);
		}
		instLoad(branch,preIssueBufCurr.getCount());

		if(checkHazard(preIssueBufCurr.getCount()) != 0){
			int opCode;
			opCode = calcOpCode(branch.getInput());
			switch (opCode){
				case 18:{ 
					if(regStore[branch.getRtReg()] == regStore[branch.getRsReg()]){
						pgmCtr = pgmCtr +4 +branch.getImmedte() * 4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
					else{
						pgmCtr+=4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
				}
				case 20:{ 
					if(regStore[branch.getRsReg()] > 0){
						pgmCtr = pgmCtr +4 +branch.getImmedte() * 4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
					else{
						pgmCtr+=4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
				}
				case 19:{ 
					if(regStore[branch.getRsReg()] < 0){
						pgmCtr = pgmCtr +4 +branch.getImmedte() * 4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
					else{
						pgmCtr+=4;
						executedIns = branch;
						waitingIns =null;
						return 1;
					}
				}
									
			}
		}
		return 0;
	}

	public static String printInst(disassembledIns inst){
		
	String out;
		
		if(inst == null){
			return "";
		}
		else{
		switch(inst.getOpCode()){
		case 16: 
			out =  " [" +inst.getInstruction() +" #" +inst.getImmedte2() * 4 + "]";
			return out;
		
		case 17: 
			out = " [" +inst.getInstruction() +" R" +inst.getRsReg()+"]";
			return out;
			
		case 18: 
			out = " [" +inst.getInstruction() + " R" +inst.getRsReg()+", R" +inst.getRtReg()+ ", #"+inst.getImmedte() *4 + "]";
			return out;
			
		case 19: 
			out = " [" +inst.getInstruction() + " R" +inst.getRsReg() + " #"+inst.getImmedte() *4+ "]";
			return out;
			
		case 20:
			out = " [" +inst.getInstruction() + " R" +inst.getRsReg()+", #" +inst.getImmedte() *4+ "]";
			return out;
			
		case 21:
			out = " [" +inst.getInstruction()+ "]";
			return out;
			
		case 22:
			out = " [" +inst.getInstruction() + " R" +inst.getRtReg()+", "+inst.getImmedte() +"(R" +inst.getRsReg()+")]";
			return out;
			
		case 23:
			out = " [" +inst.getInstruction() + " R"+inst.getRtReg()+", "+inst.getImmedte() +"(R" +inst.getRsReg()+")]";
			return out;
			
		case 24:
			out = " [" +inst.getInstruction() + " R"+inst.getRdReg()+", R" + inst.getRtReg()+", #" + inst.getBase()+ "]";
			return out;
			
		case 25:
			out = " [" +inst.getInstruction() + " R" +inst.getRdReg()+ ", R" +inst.getRtReg()+ ", #" + inst.getBase() + "]";
			return out;
			
		case 26:
			out = " [" +inst.getInstruction() + " R"+inst.getRdReg()+", R"+ inst.getRtReg()+", #"+inst.getBase()+ "]";
			return out;
			
		case 27:
			out = " [" +inst.getInstruction() + "]";
			return out;
			
		case 48: case 49: case 50: case 51: 
		case 52: case 53: case 54: case 55:
			out = " [" +inst.getInstruction() + " R" +inst.getRdReg()+ ", R"+inst.getRsReg()+", R" +inst.getRtReg()+ "]";
			return out;
			
		case 56: case 57: 
			case 58: case 59:
			out = " [" +inst.getInstruction() + " R" +inst.getRtReg()+ ", R" +inst.getRsReg()+", #"+inst.getImmedte()+ "]";
			return out;
		
		}
		}
		return "";
	}
	
	public static int valCalc(int sub){
		return (sub*4 +256);
	}
	
	public static int dataLoc(){
		int i;
		for(i=0; i<addressNo; i++){
			if(calcOpCode(valStore[i]) == 0 || calcOpCode(valStore[i]) == 63)
				return(valCalc(i));
		}
		return -1;
	}
	public static void printOut(int cycle){
		try (PrintWriter simulate = new PrintWriter(new BufferedWriter(new FileWriter("simulation.txt", true)))){
		int i;
		if(cycle != 1)
		simulate.println("");
		simulate.println("--------------------");
		//simulate.println("\n");	
		simulate.println("Cycle:" +cycle);
		simulate.println("");
		simulate.println("IF Unit:");
		simulate.println("\tWaiting Instruction:" +printInst(waitingIns));
		simulate.println("\tExecuted Instruction:" +printInst(executedIns));
		simulate.println("Pre-Issue Queue:");
		for(i=0;i<4;i++){
		simulate.println("\tEntry "+i+":" +printInst(preIssueBuf.PreIssueGet(i)));	
		}	
		simulate.println("Pre-ALU1 Queue:");
		for(i=0;i<2;i++){
		simulate.println("\tEntry "+i+":" +printInst(preALU1q.PreALUGet(i)));	
		}
		simulate.println("Pre-MEM Queue:" +printInst(preMEMq));		
		simulate.println("Post-MEM Queue:"+printInst(postMEMBuf));	
		simulate.println("Pre-ALU2 Queue:");
		for(i=0;i<2;i++){
		simulate.println("\tEntry "+i+":"+printInst(preALU2q.PreALUGet(i)));	
		}
		simulate.println("Post-ALU2 Queue:"+printInst(postALUBuf));	
		simulate.println("");
		simulate.println("Registers");
		simulate.println("R00:\t"+regStore[0]+"\t"+regStore[1]+"\t"+regStore[2]+"\t"+regStore[3]+"\t"+regStore[4]+"\t"+regStore[5]+"\t"+regStore[6]+"\t"+regStore[7]);
		simulate.println("R08:\t"+regStore[8]+"\t"+regStore[9]+"\t"+regStore[10]+"\t"+regStore[11]+"\t"+regStore[12]+"\t"+regStore[13]+"\t"+regStore[14]+"\t"+regStore[15]);
		simulate.println("R16:\t"+regStore[16]+"\t"+regStore[17]+"\t"+regStore[18]+"\t"+regStore[19]+"\t"+regStore[20]+"\t"+regStore[21]+"\t"+regStore[22]+"\t"+regStore[23]);
		simulate.println("R24:\t"+regStore[24]+"\t"+regStore[25]+"\t"+regStore[26]+"\t"+regStore[27]+"\t"+regStore[28]+"\t"+regStore[29]+"\t"+regStore[30]+"\t"+regStore[31]);
		simulate.println("");
		int addrsData = dataLoc();
		int sub = adr2sub(addrsData);
		simulate.println("Data");
		simulate.print(addrsData+":");
		for(i=0;i<8;i++){
			simulate.print("\t"+valStore[sub++]);
		}
		simulate.println("");
		if(sub < lastIns) {
		simulate.print((addrsData+32) +":");
		for(i=0;i<8;i++){
			simulate.print("\t"+valStore[sub++]);
		}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int insFetchDecode(int cycle){
		executedIns = null;
		int freeSlot;
		int fetchCount=0;
		//simulate.println(preIssueBuf.getCount());
		freeSlot= 4 - preIssueBuf.getCount();
		int i;
		 disassembledIns new_inst;

		for(i=0;i<2;i++){
			if(freeSlot ==0){
				return fetchCount;
			}

			if(waitingIns !=null){ 
				execBranchInst(waitingIns);
				return fetchCount;
			}
			//System.out.println("Program Counter"+pgmCtr);
			//System.out.println("adr2Sub" +adr2sub(pgmCtr));
			new_inst = disassembledInsCurr(valStore[adr2sub(pgmCtr)]);

			int opCode;
			opCode = calcOpCode(new_inst.getInput());
			//simulate.println("opCode value is : "+opCode);
			switch (opCode){
				case 27:{ 
					pgmCtr +=4;
					executedIns = new_inst;
					fetchCount++;
					return fetchCount;
				}
				case 16:{ 
					pgmCtr = new_inst.getImmedte2() * 4;
					executedIns = new_inst;
					fetchCount++;
					return fetchCount;
				}
				case 21:{ 
					executedIns = new_inst;
					issueInst();
					alu1Unit();
					alu2Unit();
					memoryUnitExec();
					wbUnitExec();
					printOut(cycle); 
					System.exit(0);
				}
				case 17:{ 
					
					pgmCtr = regStore[new_inst.getRsReg()];
					//System.out.println(regStore[(int) new_inst.getVal()]);
					//System.out.println("Program Counter at 934"+pgmCtr);
					executedIns = new_inst;
					fetchCount++;
					return fetchCount;
				}
				
				case 18: case 19: case 20:{
					if(execBranchInst(new_inst) != 0){ 
						fetchCount++;
						return fetchCount;
					}else{ 
						waitingIns= new_inst;
						fetchCount++;
						return fetchCount;
					}
				}
			}
			//simulate.println("New ins value is : "+new_ins);
			loadPreIssueBuf(new_inst);
			fetchCount++;
			freeSlot--;
			pgmCtr+=4;
		}
		return fetchCount;
	}
	
	public static int alu1Unit(){ 
		 disassembledIns inst;
		inst = preALU1qCurr.PreALUGet(0);
		if(inst == null){
			//simulate.println("In Alu1");
			preMEMq=null;
			return 0;
		}
		else{
			inst.setAddr(regStore[inst.getRsReg()] + inst.getImmedte());
			remPreALU(preALU1q);
			preMEMq = inst;
			return inst.getAddr();
		}
	}
	
	public static long memoryUnitExec(){ 
		disassembledIns inst;
		inst = preMEMqCurr;
		if(inst == null){
			postMEMBuf=null;
			return 0;
		}
		else{
			int opCode;
			opCode =calcOpCode(inst.getInput());
			if(opCode == 22){ //STORE
				valStore[adr2sub(inst.getAddr())] = regStore[inst.getRtReg()];
				return inst.getInput();
			}
			else if(opCode == 23){ //LOAD
				inst.setVal(valStore[adr2sub(inst.getAddr())]);
				//System.out.println(valStore[adr2sub(inst.getAddr())]);
				postMEMBuf= inst;
				return inst.getInput();
			}
		}
		return 0;
	}
	
	public static int adr2sub(int addrs){
		return (addrs - 256)/4;
	}
	
	public static int alu2Unit(){
		disassembledIns inst;
		//simulate.println("in ALu2"+preALU2qCurr.getCount());
		inst = preALU2qCurr.PreALUGet(0);
		//simulate.println("At 1043 "+ preALU2qCurr.PreALUGet(0));
		if(inst == null){
			postALUBuf=null;
			return 0;
		}
		else{
			if( (inst.setVal(executeALU2(inst))) == -1) 
			{
				//simulate.println("I am here at 1048");
				return 0;
			}
			//simulate.println("I am here at 1051");	
			remPreALU(preALU2q);
			postALUBuf = inst;
			return (int) inst.getVal();
		}
	}
	
	public static void initialise(){
		preIssueBuf=new PreIssueBuf();
		preALU1q=new PreALU();
		preALU2q=new PreALU();
		preMEMq=new disassembledIns();	
		postALUBuf=new disassembledIns();
		postMEMBuf=new disassembledIns();	
		waitingIns= null;
		executedIns= null;
	}
	
	public static void main(String[] args) throws IOException {
		//Mapper mapper = new DozerBeanMapper();
		File inputFile=null;
		if (0 < args.length) 
		{
	      inputFile = new File(args[0]);
	    }
		BufferedReader bfr = null;
		try {
			String lineOfInst, lineSub;
			bfr = new BufferedReader(new FileReader(inputFile));
			int i =0;
			         while ((lineOfInst = bfr.readLine()) != null) 
			            {
			        	lineSub = lineOfInst.substring(0,32);
			        	//simulate.println(lineSub);
			        	int temp = 0;
			        	temp = (int)Long.parseLong(signExtend(lineSub), 2);
			            //simulate.println(temp);
			            valStore[i] = temp;
			            i++;
			            lastIns = i;
			            }
			}catch (IOException e) { e.printStackTrace();}
		initialise();
		int c;
		  try{
		        String tempFile = "simulation.txt";
		        //Delete if tempFile exists
		        File fileTemp = new File(tempFile);
		          if (fileTemp.exists()){
		             fileTemp.delete();
		          }   
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }
		f
			//simulate.println("In loop "+i);
			//simulate.println(" Near loop "+ preIssueBuf.getCount());
			preIssueBufCurr = new PreIssueBuf(preIssueBuf);
			preALU1qCurr= new PreALU(preALU1q);
			preALU2qCurr = new PreALU(preALU2q);
			
	
				preMEMqCurr = new disassembledIns(preMEMq);
			
				postALUBufCurr = new disassembledIns(postALUBuf);	
			
				postMEMBufCurr= new disassembledIns(postMEMBuf);
				
			insFetchDecode(c);
			issueInst();
			alu1Unit();
			alu2Unit();
			memoryUnitExec();
			wbUnitExec();
			printOut(c);
		
        
	}
	
	}

