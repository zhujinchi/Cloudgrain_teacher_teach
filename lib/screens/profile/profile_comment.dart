import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/student_comment_cell.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_datetime_picker/flutter_datetime_picker.dart';

class ProfileCommentScreen extends StatefulWidget {
  @override
  _ProfileCommentScreenState createState() => _ProfileCommentScreenState();
}

class _ProfileCommentScreenState extends State<ProfileCommentScreen> {
  DateTime commentDay = DateTime.now();
  dynamic appraiseList = '';
  @override
  void initState() {
    super.initState();
    _refresh();
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
          title: Text(
            '老师评语',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
          actions: <Widget>[
            InkWell(
              onTap: () {
                DatePicker.showDatePicker(context,
                    // 是否展示顶部操作按钮
                    showTitleActions: true,
                    // change事件
                    onChanged: (date) {
                  this.commentDay = date;
                },
                    // 确定事件
                    onConfirm: (date) {
                  setState(() {
                    this.commentDay = date;
                    _refresh();
                  });
                },
                    // 当前时间
                    currentTime: DateTime.now(),
                    // 语言
                    locale: LocaleType.zh);
              },
              child: Center(
                child: Text(
                  '选择日期   ',
                  style: TextStyle(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      fontSize: 16.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ),
              highlightColor: Colors.transparent, // 透明色
              splashColor: Colors.transparent, // 透明色
            )
          ],
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: _buildCommentScrollView(),
        ));
  }

  CustomScrollView _buildCommentScrollView() {
    if (appraiseList.length == 0) {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          // _buildEmptyView(),
          _buildTopView(),
          _buildCommentList(),
          _buildBottomView(),
        ],
      );
    }
  }

  SliverToBoxAdapter _buildEmptyView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 722.w,
        height: 696.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 212.w, top: 205.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 227.w),
              child: Text(
                '当前日期没有评语',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 256.w),
              child: Text(
                '和学生交流吧～',
                style: TextStyle(
                    color: Color.fromRGBO(155, 157, 161, 1),
                    fontSize: 12.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  SliverFixedExtentList _buildCommentList() {
    return SliverFixedExtentList(
      itemExtent: 189.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
              width: 722.w,
              height: 189.w,
              padding: EdgeInsets.fromLTRB(33.w, 8.w, 33.w, 8.w),
              color: Colors.white,
              child: StudentCommentCell(
                studentName: appraiseList[index]['stuName'],
                commentTime: '10月23日 19:00',
                commentContent: appraiseList[index]['teacherAppraise'],
              )),
        );
      }, childCount: appraiseList.length),
    );
  }

  Widget _buildTopView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 16.w,
        color: Colors.white,
      ),
    );
  }

  Widget _buildBottomView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 62.w,
        color: Colors.white,
      ),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfAppraise();
      return null;
    });
  }

  void setDataOfAppraise() {
    String paramYear = commentDay.year.toString();
    String paramMonth = commentDay.month.toString();
    String paramDay = commentDay.day.toString();

    String startDay = '$paramYear-$paramMonth-$paramDay 00:00:00';
    String endDay = '$paramYear-$paramMonth-$paramDay 23:59:59';

    //网络请求
    FormData params = FormData.fromMap({
      'pageNumber': 1,
      'pageSize': 8,
      'startDate': startDay,
      'endDate': endDay,
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
    DioManager.getInstance().post("/class/teacher/complete/appraise", params,
        (result) {
      setState(() {
        this.appraiseList = result['data']['records'];
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
