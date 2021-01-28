import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_custom_calendar/flutter_custom_calendar.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_datetime_picker/flutter_datetime_picker.dart';

class ClassEstablishScreen extends StatefulWidget {
  @override
  _ClassEstablishScreenState createState() => _ClassEstablishScreenState();
}

class _ClassEstablishScreenState extends State<ClassEstablishScreen>
    with SingleTickerProviderStateMixin {
  int classPrice = 50;
  String classTitle;

  DateTime classDayStart = DateTime.parse('2000-01-01');
  DateTime classDayEnd = DateTime.parse('2000-01-01');
  DateTime classHourStart = DateTime.parse('2000-01-01');
  DateTime classHourEnd = DateTime.parse('2000-01-01');

  DateModel initModel = DateModel.fromDateTime(DateTime.parse('2000-01-01'));

  String classContent;
  int classNumber = 0;
  String finalPrice;
  final _titleEditingController = TextEditingController();
  final _contentEditingController = TextEditingController();
  final _numberEditingController = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    _titleEditingController.dispose();
    _contentEditingController.dispose();
    _numberEditingController.dispose();
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
            '建课',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 20.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: GestureDetector(
            onTap: () {
              FocusScopeNode currentFocus = FocusScope.of(context);
              if (!currentFocus.hasPrimaryFocus &&
                  currentFocus.focusedChild != null) {
                FocusManager.instance.primaryFocus.unfocus();
              }
            },
            child: RefreshIndicator(
              onRefresh: _refresh,
              child: CustomScrollView(
                slivers: <Widget>[
                  _buildView(),
                ],
              ),
            )));
  }

  SliverToBoxAdapter _buildView() {
    return SliverToBoxAdapter(
      child: Stack(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 32.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '课时费',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 52.w, top: 73.w),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: <Widget>[
                  Text('¥',
                      style: TextStyle(
                        color: Color.fromRGBO(0, 0, 0, 0.5),
                        fontSize: 20.sp,
                        fontFamily: 'PingFangSC-Light',
                        fontWeight: FontWeight.w300,
                      )),
                  Container(
                    width: 12.w,
                  ),
                  Text(User.shared().userTutorNight['price'].toString(),
                      style: TextStyle(
                        color: Color.fromRGBO(0, 145, 255, 1),
                        fontSize: 26.sp,
                        fontFamily: 'PingFangSC-Light',
                        fontWeight: FontWeight.w500,
                      )),
                  Text('/h',
                      style: TextStyle(
                        color: Color.fromRGBO(0, 0, 0, 0.5),
                        fontSize: 14.sp,
                        fontFamily: 'PingFangSC-Light',
                      )),
                ],
              )),
          Padding(
            padding: EdgeInsets.only(left: 52.w, top: 278.w),
            child: Image.asset(
              'assets/icons/kk_icon_date@3x.png',
              width: 18.w,
              height: 18.w,
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 135.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '课程名称',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 82.w, top: 277.w),
            child: Text(
              '课程日期',
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1),
                  fontSize: 14.w,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 96.w, top: 313.w),
              child: InkWell(
                onTap: () {
                  DatePicker.showDatePicker(context,
                      // 是否展示顶部操作按钮
                      showTitleActions: true,
                      // 最小时间
                      minTime: DateTime.now().add(new Duration(days: 1)),
                      // 最大时间
                      maxTime: DateTime(2030, 1, 1),
                      // change事件
                      onChanged: (date) {},
                      // 确定事件
                      onConfirm: (date) {
                    setState(() {
                      if (!isSetDayStart()) {
                        this.classDayStart = date;
                      } else if (date.isBefore(classDayEnd)) {
                        this.classDayStart = date;
                      } else {
                        showCupertinoDialog(
                          context: context,
                          builder: (context) {
                            return CupertinoAlertDialog(
                              title: Text('日期设置错误'),
                              content: Text('\n课程开始日期需要在结束日期之前'),
                              actions: <Widget>[
                                CupertinoDialogAction(
                                  child: Text('确认'),
                                  onPressed: () {
                                    Navigator.of(context).pop();
                                  },
                                ),
                              ],
                            );
                          },
                        );
                      }
                    });
                  },
                      // 当前时间
                      currentTime: DateTime.now(),
                      // 语言
                      locale: LocaleType.zh);
                },
                child: Text(
                  // this.classDayStart == DateTime.parse('2000-00-00')
                  //     ?
                  this.classDayStart.month.toString() +
                      "月" +
                      this.classDayStart.day.toString() +
                      "日"
                  // : '选择日期'
                  ,
                  style: TextStyle(
                      color: Color.fromRGBO(155, 157, 161, 1),
                      fontSize: 16.w,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 52.w, top: 339.w),
            child: Container(
              width: 150.w,
              height: 1.w,
              color: Color.fromRGBO(201, 204, 210, 1),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 236.w, top: 313.w),
            child: Text(
              '至',
              style: TextStyle(
                  color: Color.fromRGBO(39, 39, 85, 1),
                  fontSize: 18.w,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 331.w, top: 313.w),
              child: InkWell(
                onTap: () {
                  DatePicker.showDatePicker(context,
                      // 是否展示顶部操作按钮
                      showTitleActions: true,
                      // 最小时间
                      minTime: DateTime.now().add(new Duration(days: 1)),
                      // 最大时间
                      maxTime: DateTime(2030, 1, 1),
                      // change事件
                      onChanged: (date) {
                    //print('change $date');
                  },
                      // 确定事件
                      onConfirm: (date) {
                    setState(() {
                      if (!isSetDayStart()) {
                        this.classDayEnd = date;
                      } else if (classDayStart.isBefore(date)) {
                        this.classDayEnd = date;
                      } else {
                        showCupertinoDialog(
                          context: context,
                          builder: (context) {
                            return CupertinoAlertDialog(
                              title: Text('日期设置错误'),
                              content: Text('\n课程开始日期需要在结束日期之前'),
                              actions: <Widget>[
                                CupertinoDialogAction(
                                  child: Text('确认'),
                                  onPressed: () {
                                    Navigator.of(context).pop();
                                  },
                                ),
                              ],
                            );
                          },
                        );
                      }
                    });
                  },
                      // 当前时间
                      currentTime: DateTime.now(),
                      // 语言
                      locale: LocaleType.zh);
                },
                child: Text(
                  // this.classDayEnd == DateTime.parse('2000-00-00')
                  //     ?
                  this.classDayEnd.month.toString() +
                      "月" +
                      this.classDayEnd.day.toString() +
                      "日"
                  // : '选择日期'
                  ,
                  style: TextStyle(
                      color: Color.fromRGBO(155, 157, 161, 1),
                      fontSize: 16.w,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 287.w, top: 339.w),
            child: Container(
              width: 150.w,
              height: 1.w,
              color: Color.fromRGBO(201, 204, 210, 1),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 239.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '课程时间',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 516.w, top: 278.w),
            child: Image.asset(
              'assets/icons/kk_icon_time@3x.png',
              width: 18.w,
              height: 18.w,
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 546.w, top: 276.w),
            child: Text(
              '课程时间',
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1),
                  fontSize: 14.w,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 560.w, top: 312.w),
              child: InkWell(
                onTap: () {
                  DatePicker.showTime12hPicker(context,
                      // 是否展示顶部操作按钮
                      showTitleActions: true,
                      // change事件
                      onChanged: (date) {
                    this.classHourStart = date;
                  },
                      // 确定事件
                      onConfirm: (date) {
                    setState(() {
                      if (!isSetTimeStart()) {
                        this.classHourStart = date;
                      } else if (date.isBefore(classHourEnd)) {
                        this.classHourStart = date;
                      } else {
                        showCupertinoDialog(
                          context: context,
                          builder: (context) {
                            return CupertinoAlertDialog(
                              title: Text('时间设置错误'),
                              content: Text('\n课程开时间需要在结束时间之前'),
                              actions: <Widget>[
                                CupertinoDialogAction(
                                  child: Text('确认'),
                                  onPressed: () {
                                    Navigator.of(context).pop();
                                  },
                                ),
                              ],
                            );
                          },
                        );
                      }
                    });
                  },
                      // 当前时间
                      currentTime: DateTime.now(),
                      // 语言
                      locale: LocaleType.zh);
                },
                child: Text(
                  // this.classHourStart == DateTime.parse('2000-00-00')
                  //     ?
                  this.classHourStart.hour.toString() +
                      ":" +
                      this.classHourStart.minute.toString()
                  // : '选择时间'
                  ,
                  style: TextStyle(
                      color: Color.fromRGBO(155, 157, 161, 1),
                      fontSize: 16.w,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 516.w, top: 338.w),
            child: Container(
              width: 150.w,
              height: 1.w,
              color: Color.fromRGBO(201, 204, 210, 1),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 700.w, top: 312.w),
            child: Text(
              '至',
              style: TextStyle(
                  color: Color.fromRGBO(39, 39, 85, 1),
                  fontSize: 18.w,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 796.w, top: 312.w),
              child: InkWell(
                onTap: () {
                  DatePicker.showTime12hPicker(context,
                      // 是否展示顶部操作按钮
                      showTitleActions: true,
                      // change事件
                      onChanged: (date) {
                    // setState(() {
                    //   this.classHourEnd = date;
                    // });
                  },
                      // 确定事件
                      onConfirm: (date) {
                    setState(() {
                      if (!isSetTimeStart()) {
                        this.classHourEnd = date;
                      } else if (classHourStart.isBefore(date)) {
                        this.classHourEnd = date;
                      } else {
                        showCupertinoDialog(
                          context: context,
                          builder: (context) {
                            return CupertinoAlertDialog(
                              title: Text('时间设置错误'),
                              content: Text('\n课程开时间需要在结束时间之前'),
                              actions: <Widget>[
                                CupertinoDialogAction(
                                  child: Text('确认'),
                                  onPressed: () {
                                    Navigator.of(context).pop();
                                  },
                                ),
                              ],
                            );
                          },
                        );
                      }
                    });
                  },
                      // 当前时间
                      currentTime: DateTime.now(),
                      // 语言
                      locale: LocaleType.zh);
                },
                child: Text(
                  // this.classHourEnd == DateTime.parse('2000-00-00')
                  //     ?
                  this.classHourEnd.hour.toString() +
                      ":" +
                      this.classHourEnd.minute.toString()
                  // : '选择时间'
                  ,
                  style: TextStyle(
                      color: Color.fromRGBO(155, 157, 161, 1),
                      fontSize: 16.w,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 752.w, top: 338.w),
            child: Container(
              width: 150.w,
              height: 1.w,
              color: Color.fromRGBO(201, 204, 210, 1),
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 52.w, top: 172.w, right: 52.w),
              child: Container(
                child: Column(
                  children: <Widget>[
                    TextField(
                      controller: this._titleEditingController,
                      //maxLength: 2,
                      autofocus: false,
                      style: TextStyle(fontSize: 16.sp),
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(
                            left: 0.w,
                          ),
                          border: InputBorder.none,
                          hintText: '输入课题名称',
                          hintStyle: TextStyle(
                              color: Color.fromRGBO(155, 157, 161, 1),
                              fontFamily: 'PingFangSC-Medium',
                              fontSize: 16.sp)),
                      onChanged: (value) {
                        setState(() {
                          this.classTitle = value;
                        });
                      },
                    ),
                    Container(
                      width: 1000.w,
                      height: 1.w,
                      color: Color.fromRGBO(201, 204, 210, 1),
                    )
                  ],
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 376.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '课程内容',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 52.w, top: 414.w, right: 52.w),
              child: Container(
                child: Column(
                  children: <Widget>[
                    TextField(
                      controller: this._contentEditingController,
                      //maxLength: 2,
                      autofocus: false,
                      style: TextStyle(fontSize: 16.sp),
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(
                            left: 0.w,
                          ),
                          border: InputBorder.none,
                          hintText: '输入课程内容',
                          hintStyle: TextStyle(
                              color: Color.fromRGBO(155, 157, 161, 1),
                              fontFamily: 'PingFangSC-Medium',
                              fontSize: 16.sp)),
                      onChanged: (value) {
                        setState(() {
                          this.classContent = value;
                        });
                      },
                    ),
                    Container(
                      width: 1000.w,
                      height: 1.w,
                      color: Color.fromRGBO(201, 204, 210, 1),
                    )
                  ],
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 476.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '班级人数',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 52.w, top: 514.w, right: 812.w),
              child: Container(
                child: Column(
                  children: <Widget>[
                    TextField(
                      controller: this._numberEditingController,
                      //maxLength: 2,
                      autofocus: false,
                      keyboardType: TextInputType.phone,
                      style: TextStyle(fontSize: 16.sp),
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(
                            left: 0.w,
                          ),
                          border: InputBorder.none,
                          hintText: '输入班级人数',
                          hintStyle: TextStyle(
                              color: Color.fromRGBO(155, 157, 161, 1),
                              fontFamily: 'PingFangSC-Medium',
                              fontSize: 16.sp)),
                      onChanged: (value) {
                        setState(() {
                          this.classNumber = int.parse(value);
                        });
                      },
                    ),
                    Container(
                      width: 1000.w,
                      height: 1.w,
                      color: Color.fromRGBO(201, 204, 210, 1),
                    )
                  ],
                ),
              )),
          Padding(
            padding: EdgeInsets.only(left: 214.w, top: 526.w),
            child: Text(
              '人',
              style: TextStyle(
                  color: Color.fromRGBO(15, 32, 67, 1), fontSize: 18.sp),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 577.w),
            child: Row(
              children: <Widget>[
                Container(
                  width: 6.w,
                  height: 12.w,
                  decoration: BoxDecoration(
                    color: Color.fromRGBO(104, 212, 185, 1),
                    borderRadius: BorderRadius.circular(3.w),
                  ),
                ),
                Container(
                  width: 12.w,
                ),
                Text(
                  '最终金额',
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 18.w,
                      fontFamily: 'PingFangSC-Medium'),
                )
              ],
            ),
          ),
          Padding(
              padding: EdgeInsets.only(left: 52.w, top: 617.w),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.end,
                children: <Widget>[
                  Text('¥',
                      style: TextStyle(
                        color: Color.fromRGBO(0, 0, 0, 0.5),
                        fontSize: 20.sp,
                        fontFamily: 'PingFangSC-Light',
                        fontWeight: FontWeight.w300,
                      )),
                  Container(
                    width: 12.w,
                  ),
                  Text(priceWithClassCount(),
                      style: TextStyle(
                        color: Color.fromRGBO(0, 145, 255, 1),
                        fontSize: 26.sp,
                        fontFamily: 'PingFangSC-Light',
                        fontWeight: FontWeight.w500,
                      )),
                  Text('/人',
                      style: TextStyle(
                        color: Color.fromRGBO(0, 0, 0, 0.5),
                        fontSize: 14.sp,
                        fontFamily: 'PingFangSC-Light',
                      )),
                ],
              )),
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 696.w, bottom: 39.w),
            child: Container(
              //color: Colors.red,
              width: 936.w,
              height: 44.w,
              margin: EdgeInsets.only(top: 18.w),
              padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
              child: MaterialButton(
                child: Text(
                  "建课",
                  style: TextStyle(
                    color: Color.fromRGBO(255, 255, 255, 1),
                    fontFamily: 'PingFangSC-Semibold',
                    fontSize: 16.sp,
                  ),
                ),
                disabledColor: Color.fromRGBO(201, 204, 210, 1),
                color: Color.fromRGBO(30, 94, 255, 1),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(6.w)),
                //borderSide: BorderSide(color: Colors.orange, width: 1),
                onPressed: _titleEditingController.text.isNotEmpty &&
                        _contentEditingController.text.isNotEmpty &&
                        _numberEditingController.text.isNotEmpty
                    ? () {
                        setDataOfEstablishedClass();
                      }
                    : null,
              ),
            ),
          )
        ],
      ),
    );
  }

  bool isSetDayStart() {
    DateModel startDay = DateModel.fromDateTime(classDayStart);
    DateModel endDay = DateModel.fromDateTime(classDayEnd);

    return (startDay.isSameWith(initModel) || endDay.isSameWith(initModel))
        ? false
        : true;
  }

  bool isSetTimeStart() {
    DateModel startTime = DateModel.fromDateTime(classHourStart);
    DateModel endTime = DateModel.fromDateTime(classHourEnd);

    return (startTime.isSameWith(initModel) || endTime.isSameWith(initModel))
        ? false
        : true;
  }

  String priceWithClassCount() {
    DateModel startDay = DateModel.fromDateTime(classDayStart);
    DateModel endDay = DateModel.fromDateTime(classDayEnd);

    if (startDay.isSameWith(initModel) || endDay.isSameWith(initModel)) {
      return '0';
    }
    double price = User.shared().price;
    DateTime tempTime;
    int count = 1;

    while (!startDay.isSameWith(endDay)) {
      if (!startDay.isWeekend) {
        count++;
      }
      tempTime = DateTime(startDay.year, startDay.month, startDay.day)
          .add(new Duration(days: 1));
      startDay = DateModel.fromDateTime(tempTime);
    }
    return (count * price).toString();
  }

  void setDataOfEstablishedClass() {
    String classStartyear = classDayStart.year.toString();
    String classStartmonth = classDayStart.month.toString();
    String classStartday = classDayStart.day.toString();
    String classEndyear = classDayEnd.year.toString();
    String classEndmonth = classDayEnd.month.toString();
    String classEndday = classDayEnd.day.toString();

    String startData =
        "${classStartyear}-${classStartmonth}-${classStartday} 00:00:00";
    String endData = "${classEndyear}-${classEndmonth}-${classEndday} 00:00:00";

    String classStartHour = classHourStart.hour.toString();
    String classStartMinute = classHourStart.minute.toString();
    String classEndHour = classHourEnd.hour.toString();
    String classEndMinute = classHourEnd.minute.toString();
    String timeTag =
        "[{\"week\":\"1\",\"startTime\":\"$classStartHour:$classStartMinute:00\",\"endTime\":\"$classEndHour:$classEndMinute:00\"},{\"week\":\"2\",\"startTime\":\"$classStartHour:$classStartMinute:00\",\"endTime\":\"$classEndHour:$classEndMinute:00\"},{\"week\":\"3\",\"startTime\":\"$classStartHour:$classStartMinute:00\",\"endTime\":\"$classEndHour:$classEndMinute:00\"},{\"week\":\"4\",\"startTime\":\"$classStartHour:$classStartMinute:00\",\"endTime\":\"$classEndHour:$classEndMinute:00\"},{\"week\":\"5\",\"startTime\":\"$classStartHour:$classStartMinute:00\",\"endTime\":\"$classEndHour:$classEndMinute:00\"}]";

    //网络请求
    FormData params = FormData.fromMap({
      'amount': 10,
      'classType': 1,
      'coursesTime': timeTag,
      'depict': this.classContent,
      'endDate': endData,
      'name': this.classTitle,
      'startDate': startData,
      'stuNum': this.classNumber,
      'totalPrice': 50,
      'unitPrice': 50,
    });
    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,
    };

    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/teacher/save", params, (result) {
      print('object');
      print(result);
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('建课成功'),
            content: Text('\n您已成功创建课程'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                  Navigator.pop(context);
                },
              ),
            ],
          );
        },
      );
      //验证通过提交数据
    }, (error) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('建课失败'),
            content: Text(error),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    });
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 3), () {
      print('刷新');
      setState(() {});
      return null;
    });
  }
}
