## Feature
- TopNavBar 支持左边最多添加2个icon以及左边Title
- AbsActivity 增加通用的BaseFragment
- AbsActivity 增加通用BaseListActivity, 基于RecyclerView
- AbsActivity 增加通用全局的两种loading, 分别是全屏可操作可取消的， 全屏不可操作，不可取消的
- AbsActivity 支持Android 5.0以上可以配置状态栏颜色、可沉浸式， 可动态改变状态颜色;
- 增加通用BottomTabBar的library, 支持滑动切换底部Tab, 懒加载
- 增加ActivityStater的library, 简化Intent启动Activity的方式以及安全，减少activity中从intent中获取传参的模板代码

## Issue
- TopNavBar中间Title过长被截取的问题