class Solution {
    public boolean isValid(String s) {
        Stack<String> stack = new Stack<String>();  // Stack
        String all = "()[]{}";
        String sub = ")}]";
        if(s.length()==0){
            return true;
        }
        for(int i=0;i<s.length();i++){
            if(stack.empty()){
                stack.push(Character.toString(s.charAt(i)));    // Character to String
            }
            else{
                if(sub.indexOf(s.charAt(i))==-1){
                    stack.push(Character.toString(s.charAt(i)));
                }
                else{
                    String tmp = stack.peek() + Character.toString(s.charAt(i)); 
                    if(all.indexOf(tmp)==-1){
                        return false;
                    }
                    else{
                        stack.pop();
                    }
                }
            }
        }
        if(stack.empty()){
            return true;
        }
        else{
            return false;
        }
    }
}