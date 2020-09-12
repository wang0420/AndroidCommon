package com.android.newcommon.monitor.entity;

/**
 * @author wangwei
 * @date 2020/9/12.
 */
public class ReportEntity {

    /**
     * type : fluency
     * name : 流畅度
     * fps : {"fps":"fps","scene":"页面哈希值","desc":"页面文字描述"}
     * memory : {"total":"数字类型|总内存","free":"可用内存"}
     * cpu : {"usage":"数字类型|已用cpu","count":"数字类型|cpu核数","name":"cpu名称","architecture":"cpu架构"}
     */

    private String type;
    private String name;
    private FpsBean fps;
    private MemoryBean memory;
    private CpuBean cpu;

    public static class FpsBean {
        /**
         * fps : fps
         * scene : 页面哈希值
         * desc : 页面文字描述
         */

        private double fps;
        private int scene;
        private String desc;

        public double getFps() {
            return fps;
        }

        public void setFps(double fps) {
            this.fps = fps;
        }

        public int getScene() {
            return scene;
        }

        public void setScene(int scene) {
            this.scene = scene;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class MemoryBean {
        /**
         * total : 总内存
         * free : 可用内存
         * userMemory :已用内存
         */

        private double total;
        private double free;
        private double userMemory;

        public double getUserMemory() {
            return userMemory;
        }

        public void setUserMemory(double userMemory) {
            this.userMemory = userMemory;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getFree() {
            return free;
        }

        public void setFree(double free) {
            this.free = free;
        }
    }

    public static class CpuBean {
        /**
         * usage : 数字类型|已用cpu
         * count : 数字类型|cpu核数
         * name : cpu名称
         * architecture : cpu架构
         */

        private String usage;
        private int count;
        private String name;
        private String architecture;

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArchitecture() {
            return architecture;
        }

        public void setArchitecture(String architecture) {
            this.architecture = architecture;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FpsBean getFps() {
        return fps;
    }

    public void setFps(FpsBean fps) {
        this.fps = fps;
    }

    public MemoryBean getMemory() {
        return memory;
    }

    public void setMemory(MemoryBean memory) {
        this.memory = memory;
    }

    public CpuBean getCpu() {
        return cpu;
    }

    public void setCpu(CpuBean cpu) {
        this.cpu = cpu;
    }
}
