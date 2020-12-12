import '../widgets/programme.dart';

final list_tool = [
  {'assets/icons/icon_home_pre@3x.png': '辅导'},
  {'assets/images/icon_home_pre@3x.png': '云课'},
  {'assets/images/icon_home_pre@3x.png': '辅导'},
  {'assets/images/icon_home_pre@3x.png': '云课'},
];

final List<Map> imageList = [
  {
    "url":
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1581401773519&di=efc809f404c42c8de28f7f829f1d0b51&imgtype=0&src=http%3A%2F%2F01.minipic.eastday.com%2F20170407%2F20170407003743_1f4967f106ba9dd87277f8d5969dc711_5.jpeg"
  },
  {
    "url":
        "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2040389276,2611098741&fm=26&gp=0.jpg"
  },
  {
    "url":
        "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1629251846,4126644826&fm=26&gp=0.jpg"
  }
];

String imageStringWith(String originString) {
  int stringLength = originString.length;
  if (originString.startsWith("(")) {
    originString = originString.substring(1);
    stringLength--;
  }
  if (originString.endsWith(")")) {
    originString = originString.substring(0, stringLength - 1);
  }
  return originString;
}

/// 喜马拉雅 - 相声节目清单
const List<ProgrammeViewModel> programmeList = [
  ProgrammeViewModel(
    title: '笑坛三巨匠之一：郭德纲最新高清相声集',
    playsCount: 363182465,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group61/M0A/5D/74/wKgMcF0IoUmCLEZIAAfqML_y44E351.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '德云社相声十年经典之一',
    playsCount: 10236432,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group43/M01/AC/C8/wKgKklskehLi4XS1AARLpcjABqA907.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '郭德纲经典相声',
    playsCount: 8628885,
    needVip: true,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M02/5E/4C/wKgMaF0IomXwR0fMAAbRPUR6d-E118.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '丑女也能做皇后 | 郭德纲笑说钟无艳的绝世奇闻',
    playsCount: 35346856,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group61/M01/5D/AD/wKgMZl0Io4zSQaoqAApJnId5Fxs220.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '女妖精的一推就软？听郭德纲单口《九尾狐》',
    playsCount: 17787252,
    needVip: true,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/60/DB/wKgMaF0Ir5bAs3I6AAi5jSpprHU406.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '周文强老师财富本质课程独播',
    playsCount: 10361,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/11/7C/wKgMaF0hpLWDI57SAALanlUKN40914.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '丑女也能做皇后 | 郭德纲笑说钟无艳的绝世奇闻',
    playsCount: 35346856,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group61/M01/5D/AD/wKgMZl0Io4zSQaoqAApJnId5Fxs220.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '女妖精的一推就软？听郭德纲单口《九尾狐》',
    playsCount: 17787252,
    needVip: true,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/60/DB/wKgMaF0Ir5bAs3I6AAi5jSpprHU406.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '周文强老师财富本质课程独播',
    playsCount: 10361,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/11/7C/wKgMaF0hpLWDI57SAALanlUKN40914.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '丑女也能做皇后 | 郭德纲笑说钟无艳的绝世奇闻',
    playsCount: 35346856,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group61/M01/5D/AD/wKgMZl0Io4zSQaoqAApJnId5Fxs220.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '女妖精的一推就软？听郭德纲单口《九尾狐》',
    playsCount: 17787252,
    needVip: true,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/60/DB/wKgMaF0Ir5bAs3I6AAi5jSpprHU406.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
  ProgrammeViewModel(
    title: '周文强老师财富本质课程独播',
    playsCount: 10361,
    needVip: false,
    coverImgUrl:
        'http://imagev2.xmcdn.com/group63/M04/11/7C/wKgMaF0hpLWDI57SAALanlUKN40914.jpg!op_type=5&upload_type=album&device_type=ios&name=large&magick=png',
  ),
];
