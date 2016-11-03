package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.utils.UtilsShaPrefe;

public class InvoiceFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.ll_page_invoice)
    LinearLayout ll_page_invoice;

    @ViewInject(R.id.ll_electron_invoice)
    LinearLayout ll_electron_invoice;

    @ViewInject(R.id.ll_tax_invoice)
    LinearLayout ll_tax_invoice;

//    @ViewInject(R.id.ll_no__invoice)
//    LinearLayout ll_no__invoice;

    @ViewInject(R.id.ll_person_invoice)
    LinearLayout ll_person_invoice;

    @ViewInject(R.id.ll_unit_invoice)
    LinearLayout ll_unit_invoice;

    @ViewInject(R.id.iv_page_right)
    ImageView iv_page_right;

    @ViewInject(R.id.iv_electron_right)
    ImageView iv_electron_right;

    @ViewInject(R.id.iv_tax_right)
    ImageView iv_tax_right;

//    @ViewInject(R.id.iv_no_invoice_right)
//    ImageView iv_no_invoice_right;

    @ViewInject(R.id.iv_person_right)
    ImageView iv_person_right;

    @ViewInject(R.id.iv_unit_right)
    ImageView iv_unit_right;

    @ViewInject(R.id.rb_book)
    RadioButton rb_book;

    @ViewInject(R.id.rb_sound)
    RadioButton rb_sound;

    @ViewInject(R.id.rb_application)
    RadioButton rb_application;

    @ViewInject(R.id.rb_game)
    RadioButton rb_game;

    @ViewInject(R.id.rb_informatoin)
    RadioButton rb_informatoin;

    @ViewInject(R.id.rg)
    RadioGroup rg;

    @ViewInject(R.id.iv_invoice_back)
    ImageView iv_invoice_back;

    @ViewInject(R.id.invoiceClick)
    Button invoiceClick;

    String radionum;

    @Override
    protected void init(View view) {
        ViewUtils.inject(this, view);

        //发票类型
        ll_page_invoice.setOnClickListener(this);
        ll_electron_invoice.setOnClickListener(this);
        ll_tax_invoice.setOnClickListener(this);
        //发票 个人 单位
        ll_person_invoice.setOnClickListener(this);
        ll_unit_invoice.setOnClickListener(this);
//        ll_no__invoice.setOnClickListener(this);
        //设置第一个radioButton为默认点击
//        rb_book.setChecked(true);
        //回显发票类型
        initInvoiceShare(view);
        //返回键点击监听
        iv_invoice_back.setOnClickListener(backClickListener);
        //设置点击确定监听
        invoiceClick.setOnClickListener(invoiceClickListener);
        //对radioGroup设置监听
        rg.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.activity_invoice;
    }

    //回显发票类型
    private void initInvoiceShare(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("invoiceType", view.getContext().MODE_PRIVATE);
        String invoiceType = sp.getString("invoiceType", "");
        if ("1".equals(invoiceType)) {
            currentState = SelectorState.LEFT;
            ll_page_invoice.setSelected(true);
            iv_page_right.setVisibility(View.VISIBLE);
        } else if ("2".equals(invoiceType)) {
            currentState = SelectorState.MIDDLE;
            ll_electron_invoice.setSelected(true);
            iv_electron_right.setVisibility(View.VISIBLE);
        } else if ("3".equals(invoiceType)) {
            currentState = SelectorState.RIGHT;
            ll_tax_invoice.setSelected(true);
            iv_tax_right.setVisibility(View.VISIBLE);
        }

        SharedPreferences sp2 = view.getContext().getSharedPreferences("belong", view.getContext().MODE_PRIVATE);
        String belong = sp2.getString("belong", "");
//        if ("1".equals(belong)) {
//            isLeftSelector = true;
//            ll_person_invoice.setSelected(true);
//            iv_person_right.setVisibility(View.VISIBLE);
//        } else if ("2".equals(belong)) {
//            isLeftSelector = false;
//            ll_unit_invoice.setSelected(true);
//            iv_unit_right.setVisibility(View.VISIBLE);
//        }
        if ("1".equals(belong)) {
            belongInvoiceState = BelongInvoiceState.NOINVOICE;
//            ll_no__invoice.setSelected(true);
//            iv_no_invoice_right.setVisibility(View.VISIBLE);
        } else if ("2".equals(belong)) {
            belongInvoiceState = BelongInvoiceState.PERSONINVOCE;
            ll_person_invoice.setSelected(true);
            iv_person_right.setVisibility(View.VISIBLE);
        } else if ("3".equals(belong)) {
            belongInvoiceState = BelongInvoiceState.UNITINVOICE;
            ll_unit_invoice.setSelected(true);
            iv_unit_right.setVisibility(View.VISIBLE);
        }

        SharedPreferences sp3 = view.getContext().getSharedPreferences("radio", view.getContext().MODE_PRIVATE);
        String radio = sp3.getString("radio", "");
        if ("图书".equals(radio)) {
            rb_book.setChecked(true);
            radionum = "图书";
        } else if ("服饰".equals(radio)) {
            rb_sound.setChecked(true);
            radionum = "服饰";
        } else if ("耗材".equals(radio)) {
            rb_game.setChecked(true);
            radionum = "耗材";
        } else if ("软件".equals(radio)) {
            rb_application.setChecked(true);
            radionum = "软件";
        } else if ("资料".equals(radio)) {
            rb_informatoin.setChecked(true);
            radionum = "资料";
        }
    }

    //定义发票类型状态
    public SelectorState getCurrentState() {
        return currentState;
    }

    private SelectorState currentState = SelectorState.LEFT;

    public enum SelectorState {
        LEFT, MIDDLE, RIGHT
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发票类型
            case R.id.ll_page_invoice:
                currentState = SelectorState.LEFT;
                ll_page_invoice.setSelected(true);
                ll_electron_invoice.setSelected(false);
                ll_tax_invoice.setSelected(false);
                setRightVisibity(true);
                break;
            case R.id.ll_electron_invoice:
                currentState = SelectorState.MIDDLE;
                ll_page_invoice.setSelected(false);
                ll_electron_invoice.setSelected(true);
                ll_tax_invoice.setSelected(false);
                setRightVisibity(true);
                break;
            case R.id.ll_tax_invoice:
                currentState = SelectorState.RIGHT;
                ll_page_invoice.setSelected(false);
                ll_electron_invoice.setSelected(false);
                ll_tax_invoice.setSelected(true);
                setRightVisibity(true);
                break;
            //发票 个人 单位
//            case R.id.ll_no__invoice:
//                belongInvoiceState = BelongInvoiceState.NOINVOICE;
////                ll_no__invoice.setSelected(true);
//                ll_person_invoice.setSelected(false);
//                ll_unit_invoice.setSelected(false);
//                setRightVisibity(false);
//                break;
            case R.id.ll_person_invoice:
                belongInvoiceState = BelongInvoiceState.PERSONINVOCE;
//                ll_no__invoice.setSelected(false);
                ll_person_invoice.setSelected(true);
                ll_unit_invoice.setSelected(false);
                setRightVisibity(false);
                break;
            case R.id.ll_unit_invoice:
                belongInvoiceState = BelongInvoiceState.UNITINVOICE;
//                ll_no__invoice.setSelected(false);
                ll_person_invoice.setSelected(false);
                ll_unit_invoice.setSelected(true);
                setRightVisibity(false);
                break;
        }
    }


    //定义发票归属状态
//    boolean isLeftSelector;
//
//    public void setSelectorType(boolean isLeftSelector) {
//        this.isLeftSelector = isLeftSelector;
//        if (isLeftSelector == true) {
//            ll_person_invoice.setSelected(true);
//            ll_unit_invoice.setSelected(false);
//        } else {
//            ll_person_invoice.setSelected(false);
//            ll_unit_invoice.setSelected(true);
//        }
//    }
    private BelongInvoiceState belongInvoiceState = BelongInvoiceState.NOINVOICE;

    public BelongInvoiceState getBelongInvoiceState() {
        return belongInvoiceState;
    }

    public enum BelongInvoiceState {
        NOINVOICE, PERSONINVOCE, UNITINVOICE
    }

    //设置对勾是否可见
    private void setRightVisibity(boolean invoiceType) {
        if (invoiceType) {
            if (currentState == SelectorState.LEFT) {
                iv_page_right.setVisibility(View.VISIBLE);
                iv_electron_right.setVisibility(View.GONE);
                iv_tax_right.setVisibility(View.GONE);
            } else if (currentState == SelectorState.MIDDLE) {
                iv_page_right.setVisibility(View.GONE);
                iv_electron_right.setVisibility(View.VISIBLE);
                iv_tax_right.setVisibility(View.GONE);
            } else if (currentState == SelectorState.RIGHT) {
                iv_page_right.setVisibility(View.GONE);
                iv_electron_right.setVisibility(View.GONE);
                iv_tax_right.setVisibility(View.VISIBLE);
            }
        } else {
            if (belongInvoiceState == BelongInvoiceState.NOINVOICE) {
//                iv_no_invoice_right.setVisibility(View.VISIBLE);
                iv_person_right.setVisibility(View.GONE);
                iv_unit_right.setVisibility(View.GONE);
            } else if (belongInvoiceState == BelongInvoiceState.PERSONINVOCE) {
//                iv_no_invoice_right.setVisibility(View.GONE);
                iv_person_right.setVisibility(View.VISIBLE);
                iv_unit_right.setVisibility(View.GONE);
            } else if (belongInvoiceState == BelongInvoiceState.UNITINVOICE){
//                iv_no_invoice_right.setVisibility(View.GONE);
                iv_person_right.setVisibility(View.GONE);
                iv_unit_right.setVisibility(View.VISIBLE);
            }
        }

    }

    //点击确认返回数据到支付中心
    private View.OnClickListener invoiceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //把发票类型存到本地  电子发票 纸质发票  增值税发票
            if (currentState == SelectorState.LEFT) {
                UtilsShaPrefe.sharedPreferences("invoiceType", "invoiceType", "1", v.getContext());
            } else if (currentState == SelectorState.MIDDLE) {
                UtilsShaPrefe.sharedPreferences("invoiceType", "invoiceType", "2", v.getContext());
            } else if (currentState == SelectorState.RIGHT) {
                UtilsShaPrefe.sharedPreferences("invoiceType", "invoiceType", "3", v.getContext());
            }

            //把发票类型存到本地  不要发票 个人 单位
//            if (isLeftSelector) {
//                UtilsShaPrefe.sharedPreferences("belong", "belong", "1", v.getContext());
//            } else {
//                UtilsShaPrefe.sharedPreferences("belong", "belong", "2", v.getContext());
//            }
            if (belongInvoiceState == BelongInvoiceState.NOINVOICE) {
                UtilsShaPrefe.sharedPreferences("belong", "belong", "1", v.getContext());
            } else if (belongInvoiceState == BelongInvoiceState.PERSONINVOCE) {
                UtilsShaPrefe.sharedPreferences("belong", "belong", "2", v.getContext());
            } else {
                UtilsShaPrefe.sharedPreferences("belong", "belong", "3", v.getContext());
            }

            UtilsShaPrefe.sharedPreferences("radio", "radio", radionum, v.getContext());

            MainActivity activity = (MainActivity) getActivity();
            activity.accountOnClick();
        }
    };

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_book:
                    radionum = "图书";
                    break;
                case R.id.rb_sound:
                    radionum = "服饰";
                    break;
                case R.id.rb_game:
                    radionum = "耗材";
                    break;
                case R.id.rb_application:
                    radionum = "软件";
                    break;
                case R.id.rb_informatoin:
                    radionum = "资料";
                    break;
            }
        }
    };

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.onBackPressed();
        }
    };
}
