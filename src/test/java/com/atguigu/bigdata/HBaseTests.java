//package com.atguigu.bigdata;
//
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.*;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@SpringBootTest
//public class HBaseTests {
//
//    public static Configuration conf;
//
//    @Before
//    public void init(){
//        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "192.168.1.97,192.168.1.98,192.168.1.99");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//
//    }
//
//    @Test
//    public void isTableExist() throws IOException {
//        String tableName = "student";
//        //获取管理员对象: HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);
//        Connection connection = ConnectionFactory.createConnection(conf);
//        Admin hBaseAdmin = connection.getAdmin();
//        TableName tableName1 = TableName.valueOf(tableName);
//        System.out.println(hBaseAdmin.tableExists(tableName1));
//        hBaseAdmin.disableTable(tableName1);
//        hBaseAdmin.deleteTable(tableName1);
//        hBaseAdmin.close();
//    }
//
//    @Test
//    public void createTable() throws IOException {
//        String tableName = "student";
//        HBaseAdmin admin = new HBaseAdmin(conf);
//        //判断表是否存在
//        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
//        descriptor.addFamily(new HColumnDescriptor("info"));
//        admin.createTable(descriptor);
//    }
//
//    @Test
//    public void addRowData() throws IOException {
//        //创建HTable对象
//        HTable hTable = new HTable(conf, "student");
//        //向表中插入数据
//        Put put = new Put(Bytes.toBytes("1001"));
//        //向Put对象中组装数据
//        //put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("zhangsan"));
//        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("zhangsan"));
//        hTable.put(put);
//        hTable.close();
//        System.out.println("插入数据成功");
//    }
//
//    @Test
//    public void deleteMultiRow() throws IOException {
//        String tableName = "student";
//        HTable hTable = new HTable(conf, tableName);
//        List<Delete> deleteList = new ArrayList<Delete>();
//        Delete delete = new Delete(Bytes.toBytes("1001"));
//        deleteList.add(delete);
//        hTable.delete(deleteList);
//        hTable.close();
//    }
//
//    @Test
//    public void getAllRows() throws IOException {
//        String tableName = "student";
//        HTable hTable = new HTable(conf, tableName);
//        //得到用于扫描 region 的对象
//        Scan scan = new Scan();
//        //使用 HTable 得到 resultcanner 实现类的对象
//        ResultScanner resultScanner = hTable.getScanner(scan);
//        for (Result result : resultScanner) {
//            Cell[] cells = result.rawCells();
//            for (Cell cell : cells) {
//                //得到 rowkey
//                System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
//                //得到列族
//                System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//            }
//        }
//    }
//
//    @Test
//    public void getRow() throws IOException {
//        String tableName = "student";
//        String rowKey = "1001";
//        HTable table = new HTable(conf, tableName);
//        Get get = new Get(Bytes.toBytes(rowKey));
//        //get.setMaxVersions();显示所有版本
//        //get.setTimeStamp();显示指定时间戳的版本
//        Result result = table.get(get);
//        for (Cell cell : result.rawCells()) {
//            System.out.println("行键:" + Bytes.toString(result.getRow()));
//            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//            System.out.println("时间戳:" + cell.getTimestamp());
//        }
//    }
//
//    @Test
//    public  void getRowQualifier() throws IOException {
//        String tableName = "student";
//        String rowKey = "1001";
//        String family = "info";
//        String qualifier = "name";
//        HTable table = new HTable(conf, tableName);
//        Get get = new Get(Bytes.toBytes(rowKey));
//        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//        Result result = table.get(get);
//        for (Cell cell : result.rawCells()) {
//            System.out.println("行键:" + Bytes.toString(result.getRow()));
//            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//        }
//    }
//
//
//}
//
