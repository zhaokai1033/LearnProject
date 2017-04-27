package com.zk.sample.module.file;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.baselibrary.util.SPUtil;
import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.base.Util;
import com.zk.sample.databinding.FragmentFileBinding;

import java.io.File;
import java.io.IOException;

public class FileListFragment extends BaseFragment<FragmentFileBinding> implements LoaderManager.LoaderCallbacks<Cursor> {

    private VideoAdapter mAdapter;
    private String mPath;
    private LoaderManager loadManager;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_file;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPath = new File(mPath).getParent();
                loadData();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPath = SPUtil.getString(getContext(), "path", Environment.getExternalStorageDirectory().getPath());
        binding.pathView.setText(mPath);
        Activity activity = getActivity();
        mAdapter = new VideoAdapter(activity);
        binding.fileListView.setAdapter(mAdapter);
        binding.fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                String path = mAdapter.getFilePath(position);
                if (TextUtils.isEmpty(path))
                    return;
                clickItem(path);
            }
        });
        mAdapter = new VideoAdapter(getActivity());
        binding.fileListView.setAdapter(mAdapter);
        loadManager = getLoaderManager();
        loadData();
    }

    private void loadData() {
        SPUtil.putString(getContext(), "path", mPath);
        binding.pathView.setText(mPath);
        loadManager.restartLoader(1, null, this);

    }

    private void clickItem(String p) {
        File f = new File(p);
        try {
            f = f.getAbsoluteFile();
            //
            f = f.getCanonicalFile();
            if (TextUtils.isEmpty(f.toString()))
                f = new File("/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (f.isDirectory()) {
            mPath = f.getAbsolutePath();
            loadData();
        } else if (f.exists()) {
            new Util().openFile(getActivity(), f);
//            Intent intent = new Intent("android.intent.action.VIEW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Uri uri = Uri.fromFile(new File(mPath));
//            intent.setDataAndType(uri, "*/*");
//            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (TextUtils.isEmpty(mPath))
            return null;
        return new PathCursorLoader(getActivity(), mPath);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private final class VideoAdapter extends SimpleCursorAdapter {
        final class ViewHolder {
            ImageView iconImageView;
            TextView nameTextView;
        }

        VideoAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, null,
                    new String[]{PathCursor.CN_FILE_NAME, PathCursor.CN_FILE_PATH},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.list_item_file, parent, false);
            }

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.iconImageView = (ImageView) view.findViewById(R.id.icon);
                viewHolder.nameTextView = (TextView) view.findViewById(R.id.name);
            }

            if (isDirectory(position)) {
                viewHolder.iconImageView.setImageResource(R.mipmap.ic_theme_folder);
            } else if (isVideo(position)) {
                viewHolder.iconImageView.setImageResource(R.mipmap.ic_theme_play_arrow);
            } else {
                viewHolder.iconImageView.setImageResource(R.mipmap.ic_theme_description);
            }
            viewHolder.nameTextView.setText(getFileName(position));

            return view;
        }

        @Override
        public long getItemId(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null)
                return 0;

            return cursor.getLong(PathCursor.CI_ID);
        }

        Cursor moveToPosition(int position) {
            final Cursor cursor = getCursor();
            if (cursor.getCount() == 0 || position >= cursor.getCount()) {
                return null;
            }
            cursor.moveToPosition(position);
            return cursor;
        }

        boolean isDirectory(int position) {
            final Cursor cursor = moveToPosition(position);
            return cursor == null || cursor.getInt(PathCursor.CI_IS_DIRECTORY) != 0;

        }

        boolean isVideo(int position) {
            final Cursor cursor = moveToPosition(position);
            return cursor == null || cursor.getInt(PathCursor.CI_IS_VIDEO) != 0;

        }

        String getFileName(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null)
                return "";

            return cursor.getString(PathCursor.CI_FILE_NAME);
        }

        String getFilePath(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null)
                return "";

            return cursor.getString(PathCursor.CI_FILE_PATH);
        }
    }
}
