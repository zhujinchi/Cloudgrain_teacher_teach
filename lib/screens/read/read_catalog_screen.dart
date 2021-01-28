import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ReadCatalogScreen extends StatefulWidget {
  final String bookTitle;
  final String bookId;
  ReadCatalogScreen({Key key, this.bookTitle, this.bookId}) : super(key: key);

  @override
  _ReadCatalogScreenState createState() {
    return _ReadCatalogScreenState();
  }
}

class _ReadCatalogScreenState extends State<ReadCatalogScreen> {
  dynamic cataLogList = [];

  @override
  void initState() {
    _refresh();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        centerTitle: true,
        backgroundColor: Colors.white,
        brightness: Brightness.light,
        elevation: 0.8,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios),
          color: Colors.grey,
          iconSize: 28.0,
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          this.widget.bookTitle,
          style: TextStyle(
              color: Color.fromRGBO(15, 32, 67, 1),
              fontSize: 18.w,
              fontFamily: 'PingFangSC-Medium'),
        ),
      ),
      body: RefreshIndicator(
        onRefresh: _refresh,
        child: ListView.builder(
            itemCount: cataLogList.length,
            itemBuilder: (BuildContext context, int a) {
              return getRow(a);
            }),
      ),
    );
  }

  Widget getRow(int a) {
    return Container(
      height: 99.w,
      color: Colors.white,
      child: Stack(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: 770.w, top: 43.w),
            child: Image.asset(
              'assets/icons/yqx_icon_a@3x.png',
              width: 12.w,
              height: 12.w,
              fit: BoxFit.fill,
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 242.w, top: 24.w),
            child: Text(
              cataLogList[a].toString(),
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1),
                  fontWeight: FontWeight.bold,
                  fontSize: 18.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 242.w, top: 57.w),
            child: Text(
              '876人参加',
              style: TextStyle(
                  color: Color.fromRGBO(155, 157, 161, 1),
                  fontSize: 12.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 242.w, top: 98.w),
              child: Container(
                width: 540.w,
                height: 1.w,
                color: Color.fromRGBO(201, 204, 210, 1),
              )),
        ],
      ),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfBookCataLog();
      return null;
    });
  }

  void setDataOfBookCataLog() {
    //网络请求
    FormData params = FormData.fromMap({
      'pageNum': 1,
      'pageSize': 1000,
      'bookId': this.widget.bookId,
    });
    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().get("/reading/acton/course/catalog/page", params,
        (result) {
      setState(() {
        this.cataLogList = result['data']['records'];
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
