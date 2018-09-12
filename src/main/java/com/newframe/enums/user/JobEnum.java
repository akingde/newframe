package com.newframe.enums.user;

/**
 * @author WangBin
 */
public enum JobEnum {

    CHAIRMAN(1),        //董事长
    PRESIDENT(2),       //总裁
    MANAGINGDIRECTOR(3),//总经理
    DIVISIONMANAGER(4),//部门经理
    ;

    private Integer jobId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    JobEnum(Integer jobId) {
        this.jobId = jobId;
    }

    public static boolean isEmpty(Integer jobId){

        if (jobId == null){
            return true;
        }
        for (JobEnum job : JobEnum.values()) {
            if(job.getJobId().equals(jobId)){
                return false;
            }
        }
        return true;
    }
}