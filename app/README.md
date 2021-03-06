# 自定义toolbar



#### 属性

|       名称       |                             描述                             | 值的类型  |
| :--------------: | :----------------------------------------------------------: | :-------: |
|  isFitStatusBar  |    是否适配状态栏，如果true,则会自定填充状态栏高度的留白     |  boolean  |
|    rightIcon     |                           右侧图标                           | reference |
|     leftIcon     |                           左侧图标                           | reference |
|    titleText     |                             标题                             |  String   |
|    rightText     |                           右侧文字                           |  String   |
|     leftText     |                           左侧文字                           |  String   |
|    isShowLeft    |               是否显示左侧内容（默认返回箭头）               |  boolean  |
| isShowBottomLine |                       是否显示底部隔线                       |  boolean  |
|   isShowShadow   | 是否显示阴影（白色背景默认为true,其他背景默认为false，目前只支持5.0以上） |  boolean  |
|     bgColor      |                     背景颜色（默认白色）                     | reference |
|    textColor     |                           文字颜色                           | reference |
|  rightTextColor  |                         右侧文字颜色                         | reference |
|   shadowColor    |                          阴影的颜色                          | reference |
|  titleTextSize   |                         标题文字大小                         | dimension |
|   leftTextSize   |                         左侧文字大小                         | dimension |
|  rightTextSize   |                         右侧文字大小                         | dimension |

#### 方法

|                       名称                        |         描述         |
| :-----------------------------------------------: | :------------------: |
| setLeftOnClickListener(OnClickListener listener） |   左侧点击事件监听   |
| setRightOnClickListener(OnClickListener listener) |   右侧点击事件监听   |
|                  showTitleView()                  |       展示标题       |
|                  hideTitleView()                  |       隐藏标题       |
|                    showLeft()                     |       显示左侧       |
|                    showRight()                    |       显示右侧       |
|                    hideRight()                    |       隐藏右侧       |
|                  getRightView()                   |    获取右侧父控件    |
|                  getTitleView()                   |     获取标题控件     |
|                  getTextRight()                   |   获取右侧文本控件   |
|                    isWhiteBg()                    |     是否白色背景     |
|                 isTransparentBg()                 |     是否透明背景     |
|                setTitle(int resId)                |     设置标题内容     |
|           setTitle(CharSequence title)            |     设置标题内容     |
|               setRightText(int id)                |     设置右侧文字     |
|        setLeftBackText(CharSequence text)         | 设置带返回箭头的文字 |
|          setRightText(CharSequence text)          |     设置右侧文字     |
|                setLeftText(int id)                |     设置左侧文字     |
|          setLeftText(CharSequence text)           |     设置左侧文字     |
|              setRightIcon(int icon)               |     设置右侧图片     |
|               setLeftIcon(int icon)               |     设置左侧图片     |
|            setRightIcon(Drawable icon)            |     设置右侧图片     |
|            setLeftIcon(Drawable icon)             |     设置左侧图片     |
|                                                   |                      |

