package cn.qinguide.f5web.mvp.model;

import android.os.Build;
import android.text.TextUtils;

import com.ljy.devring.other.RingLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.imodel.IEditModel;
import cn.qinguide.f5web.xposed.share.Share;
import cn.qinguide.f5web.xposed.utils.KeyUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditModel implements IEditModel {


    @Override
    public void readFile(final String fileName, final onReadFileListener onReadFileListener) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                File file = new File(KeyUtil.SCRIPT_PATH + fileName);
                if (!file.exists())
                    onReadFileListener.error();
                else {
                    FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
                    BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
                    StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
                    String s = "";
                    while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                        sb.append(s).append("\n");//将读取的字符串添加换行符后累加存放在缓存中
                    }
                    bReader.close();
                    e.onNext(sb.toString());
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                onReadFileListener.success(s);
            }

            @Override
            public void onError(Throwable e) {
                onReadFileListener.error();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void save(final String fileName, final String supportPage, final String description, final String script, final onSaveFileListener onSaveFileListener) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                File file = new File(KeyUtil.SCRIPT_PATH + fileName);
                ScriptEntity scriptEntity;
                if (!file.exists()){
                    RingLog.i("生成文件" + file.createNewFile());
                    scriptEntity = new ScriptEntity();
                    String author = Build.DEVICE;
                    scriptEntity.setName(fileName);
                    scriptEntity.setAuthor(TextUtils.isEmpty(author) ? "本机" : author);
                    scriptEntity.setFileName(fileName);
                    scriptEntity.setSupportPage(supportPage);
                    scriptEntity.setDescription(description);
                    scriptEntity.setEnable(false);
                }else{
                    scriptEntity = (ScriptEntity) Share.getObject(fileName);
                    scriptEntity.setName(fileName);
                    scriptEntity.setSupportPage(supportPage);
                    scriptEntity.setDescription(description);
                }
                Writer out = new FileWriter(file);
                out.write(script);
                out.flush();
                out.close();
                Share.putObject(fileName, scriptEntity);
                e.onNext(R.string.save_success);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer o) {
                onSaveFileListener.success(o);
            }

            @Override
            public void onError(Throwable e) {
                onSaveFileListener.error();
            }

            @Override
            public void onComplete() {

            }
        });
    }

}