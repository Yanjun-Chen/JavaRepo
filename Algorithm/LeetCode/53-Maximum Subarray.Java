class Solution {
    public int maxSubArray(int[] nums) {
        int[] arr = new int[nums.length];
        arr[0] = nums[0]; 
        int re = arr[0];

        for(int i=1;i<nums.length;i++){
            if((arr[i-1]+nums[i])>nums[i]){
                arr[i] = arr[i-1]+nums[i];
            }
            else{
                arr[i] = nums[i];
            }
            if(re<arr[i]){
                re = arr[i];
            }
        }
        return re;
    }
}