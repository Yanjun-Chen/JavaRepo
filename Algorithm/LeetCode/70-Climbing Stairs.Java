class Solution {
    public int climbStairs(int n) {
        '''
        dynamic programming
        '''

        if(n== 1) {return 1;}
        if(n==2){return 2;}

        int a= 1;
        int b= 2;
        int c= 0;
        for(int y=3;y<= n;y++){
            c= a+b;
            a= b;
            b= c;
        }
                         
        return c;   
        }
    }