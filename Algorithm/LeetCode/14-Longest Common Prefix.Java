class Solution {
    public String longestCommonPrefix(String[] strs) {
        String re = "";
        if(strs.length==0){
            return re;
        }
        for(int i = 0; i<strs[0].length();i++){
            char z = strs[0].charAt(i);
            for(int j=0; j<strs.length;j++){
                if(i>=strs[j].length()){
                    return re;
                }
                if(strs[j].charAt(i)!=z){
                    return re;
                }
            }
            re = re + z;
        }
        return re;
    }
}