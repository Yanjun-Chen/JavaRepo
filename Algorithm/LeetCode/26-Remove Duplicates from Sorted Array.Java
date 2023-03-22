class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        if(nums.length==1){
            return 1;
        }
        int record = nums[0];
        int count = nums.length;
        int diff = 0;
        for(int i=1;i<nums.length-diff;i++){
            if(nums[i]==record){
                count--;
                func(nums,i);
                i--;
                diff++;
            }
            else{
                record = nums[i];
            }
        }
        return count;
    }
    public void func(int[] nums, int tag){
        for(int i=tag; i<nums.length-1;i++){
            nums[i] = nums[i+1];
        }
    }
}