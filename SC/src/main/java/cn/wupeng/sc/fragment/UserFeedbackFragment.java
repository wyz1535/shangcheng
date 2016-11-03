package cn.wupeng.sc.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;

/**
 * 用户反馈界面
 */
public class UserFeedbackFragment extends BaseFragment {
    @InjectView(R.id.et_import_evaluate)
    EditText etImportEvaluate;
    @InjectView(R.id.et_import_name)
    EditText etImportName;
    @InjectView(R.id.more_submit_feedback)
    Button moreSubmitFeedback;
    private String importEvaluate;
    private String importName;
    private Button bt_collection_clear;
    private TextView top_head_text;
    @Override
    protected void init(View view) {
        ButterKnife.inject(this,view);
        click(view);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_user_feedback;
    }
    private void click(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("售后服务");
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(backClickListener);
        moreSubmitFeedback.setOnClickListener(submitFeedbackListener);
    }
    //点击返回键
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.moreFragment();
        }
    };
    private View.OnClickListener submitFeedbackListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            importEvaluate =etImportEvaluate.getText().toString().trim();
            importName = etImportName.getText().toString().trim();
            Log.i("test", importEvaluate + importName);
            if (TextUtils.isEmpty(importEvaluate) || TextUtils.isEmpty(importName)) {
                Toast.makeText(v.getContext(),"内容不能为空哦!亲",Toast.LENGTH_SHORT).show();
                return;
            }
            showSubmitDialog(v);
        }
    };
    //提交反馈内容
    private void showSubmitDialog(View v) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
        dialog.setTitle("感谢亲的提交,你反馈的内容我们已看到!会及时为你处理");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                etImportEvaluate.setText(null);
                etImportName.setText(null);
            }
        });
        dialog.show();
    }
}
