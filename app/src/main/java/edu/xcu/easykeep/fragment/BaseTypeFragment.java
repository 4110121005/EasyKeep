package edu.xcu.easykeep.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.xcu.easykeep.Dialog.CalendarDialog;
import edu.xcu.easykeep.Dialog.NoteDialog;
import edu.xcu.easykeep.adapter.TypeAdapter;
import edu.xcu.easykeep.bean.BillBean;
import edu.xcu.easykeep.bean.TypeBean;
import edu.xcu.easykeep.databinding.FragmentTypeBinding;
import edu.xcu.easykeep.db.TypeDBManger;

/**
 *  记录支出/收入的基类 Fragment
 */
public abstract class BaseTypeFragment extends Fragment {
    FragmentTypeBinding binding;
    TypeDBManger typeDBManger;
    ArrayList<TypeBean> itemList;
    TypeAdapter typeAdapter;
    BillBean bill; // 需要插入到记账本中的数据
    String note = "添加备注"; //当前界面展现出的备注
    NoteDialog noteDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bill = new BillBean();
        bill.setName("其他"); // 默认类型为“其他”
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTypeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setBillDate(); // 初始化账单日期

        itemList = getAllItem(); //获取grid数据

        typeAdapter = new TypeAdapter(itemList, getContext());
        binding.typeGrid.setAdapter(typeAdapter);

        // 设置监听器
        binding.typeGrid.setOnItemClickListener(this::handleTypeItemClick);
        binding.tvRecordNote.setOnClickListener(v -> handleNoteClick());
        binding.tvRecordTime.setOnClickListener(v -> handleTimeClick());
        binding.ivAddBill.setOnClickListener(v -> saveBillTODB());

        return root;
    }

    /**
     *  获取类型数据，由子类实现
     * @return 类型数据列表
     */
    public abstract ArrayList<TypeBean> getAllItem();

    /**
     *  保存账单数据到数据库，由子类实现
     */
    public abstract void saveBillTODB();

    /**
     *  设置 GridView 每一项的点击事件
     * @param parent   AdapterView
     * @param view     View
     * @param position 点击的位置
     * @param id      id
     */
    public void handleTypeItemClick(AdapterView<?> parent, View view, int position, long id) {
        TypeBean typeBean = itemList.get(position);
        String name = typeBean.getName();

        typeAdapter.setSelectPos(position);
        typeAdapter.notifyDataSetChanged();//提示绘制发生变化

        binding.ivTypeImageTop.setImageResource(typeBean.getSelected());
        binding.tvTypeNameTop.setText(typeBean.getName());

        bill.setName(name);//设置添加的账单的属性
    }

    /**
     * 处理备注点击事件
     */
    public void handleNoteClick(){
        note = binding.tvRecordNote.getText().toString();
        if(!note.equals("添加备注")){
            noteDialog.setNote(note);
        }
        showNoteDialog();
    }

    /**
     *  处理时间点击事件
     */
    public void handleTimeClick(){
        showCalendarDialog();
    }

    /**
     *  弹出备注对话框
     */
    public void showNoteDialog(){
        if(noteDialog == null){
            noteDialog = new NoteDialog(requireContext());
        }
        noteDialog.show();
        noteDialog.setOnNoteSubmitListener(new NoteDialog.OnNoteSubmitListener(){
            @Override
            public void onNoteSubmit() {
                if(noteDialog.getNote().isEmpty()){
                    note = "添加备注";
                    binding.tvRecordNote.setText(note);
                    bill.setNote(note);
                }else{
                    note = noteDialog.getNote();
                    binding.tvRecordNote.setText(note);
                    bill.setNote(note);
                }
            }
        });
    }

    /**
     *  获取输入的账单金额
     * @return 返回输入框的金额，没有输入时，返回null。
     */
    public Float getInputMoney(){
        String m = binding.etMoney.getText().toString();
        if(m.isEmpty()){
           return null;
        }else {
            return Float.parseFloat(m);
        }
    }

    /**
     *  弹出日期选择对话框
     */
    public void showCalendarDialog(){
        CalendarDialog calendarDialog = new CalendarDialog(requireContext());
        calendarDialog.show();
        //设定确定按钮被点击了的监听器
        calendarDialog.setOnEnsureListener(new CalendarDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                // 拼接时间字符串
                String dateFormat = year + "年" + month + "月" + day + "日 " + time;
                binding.tvRecordTime.setText(dateFormat);
                bill.setTime(time);
                bill.setYear(year);
                bill.setMonth(month);
                bill.setDay(day);
            }
        });
    }

    /**
     *  获取当前日期
     * @return 当前日期字符串，yyyy年MM月dd日 HH:mm
     */
    public String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     *  设置账单日期
     */
    public void setBillDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        bill.setYear(year);
        bill.setMonth(month);
        bill.setDay(day);

        binding.tvRecordTime.setText(getCurrentDate());
        bill.setTime(binding.tvRecordTime.getText().toString().split(" ")[1]);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
