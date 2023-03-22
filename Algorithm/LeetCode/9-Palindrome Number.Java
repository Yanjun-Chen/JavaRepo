class Solution {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int y = 0;
        int z = x;
        while(x!=0){
            y = x%10 + y*10;
            x = x/10;
        }
        if(z==y){
            return true;
        }
        else{
            return false;
        }
    }
}