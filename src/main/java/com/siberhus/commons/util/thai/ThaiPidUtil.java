package com.siberhus.commons.util.thai;

public final class ThaiPidUtil {
	
    public static boolean isValidMod11(String pid) {
    	
        //Mod11 algo
        int no1 = (int) pid.charAt(0) - 48;
        int no2 = (int) pid.charAt(1) - 48;
        int no3 = (int) pid.charAt(2) - 48;
        int no4 = (int) pid.charAt(3) - 48;
        int no5 = (int) pid.charAt(4) - 48;
        int no6 = (int) pid.charAt(5) - 48;
        int no7 = (int) pid.charAt(6) - 48;
        int no8 = (int) pid.charAt(7) - 48;
        int no9 = (int) pid.charAt(8) - 48;
        int no10 = (int) pid.charAt(9) - 48;
        int no11 = (int) pid.charAt(10) - 48;
        int no12 = (int) pid.charAt(11) - 48;
        int no13 = (int) pid.charAt(12) - 48;
        int result = 0;
        result = no1 * 13;
        result = result + (no2 * 12);
        result = result + (no3 * 11);
        result = result + (no4 * 10);
        result = result + (no5 * 9);
        result = result + (no6 * 8);
        result = result + (no7 * 7);
        result = result + (no8 * 6);
        result = result + (no9 * 5);
        result = result + (no10 * 4);
        result = result + (no11 * 3);
        result = result + (no12 * 2);
        result = result % 11;
        int finalResult = (11 - result) % 10;
        if (finalResult == no13) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isThaiNationPID(String pid){
        if(pid.startsWith("6") || pid.startsWith("7")){
            return false;//not thai
        }
        //if nation is thai return true
        return true;
    }
    
    
    public static boolean isValidPid(String pid){
    	
    	if(pid==null)return false;
    	
    	pid = pid.replaceAll("\\D", "");
    	if(pid.length()==13){
    		if(isValidMod11(pid)){
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public static void main(String[] args) {
		System.out.println(isValidPid("1101700058247"));
	}
    
}
