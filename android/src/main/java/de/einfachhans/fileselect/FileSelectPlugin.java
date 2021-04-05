package de.einfachhans.fileselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(name = "FileSelect")
public class FileSelectPlugin extends Plugin {

    @PluginMethod
    public void select(PluginCall call) {
        Boolean multiple = call.getBoolean("multiple", true);
        JSArray extensions = call.getArray("extensions");

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);

        List<String> supportedMimeTypes = new ArrayList<>();
        label:
        for (int i = 0; i < extensions.length(); i++) {
            try {
                switch (extensions.getString(i)) {
                    case "images":
                        supportedMimeTypes.add("image/*");
                        break;
                    case "videos":
                        supportedMimeTypes.add("videos/*");
                        break;
                    case "audios":
                        supportedMimeTypes.add("audios/*");
                        break;
                    case "*":
                        supportedMimeTypes.add("*/*");
                        break label;
                    default:
                        supportedMimeTypes.add(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensions.getString(i)));
                        break;
                }
            } catch (JSONException e) {
                Log.i("[FileSelect]", e.getMessage());
            }
        }
        if(supportedMimeTypes.size() == 0) {
            supportedMimeTypes.add("*/*");
        }

        String type = "";
        for (String mime: supportedMimeTypes) {
            type += mime + "|";
        }
        type = type.substring(0, type.length() - 1);

        // chooseFile.putExtra(Intent.EXTRA_MIME_TYPES, supportedMimeTypes.toArray());
        chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiple);
        chooseFile.setType(type);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile = Intent.createChooser(chooseFile, "");
        startActivityForResult(call, chooseFile, "pickFilesResult");
    }

    @ActivityCallback
    private void pickFilesResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        Intent data = result.getData();
        Context context = getBridge().getActivity().getApplicationContext();

        JSArray files = new JSArray();
        if (result.getResultCode() == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    files.put(getCopyFilePath(uri, context));
                }
            } else {
                Uri uri = data.getData();
                files.put(getCopyFilePath(uri, context));
            }
            JSObject ret = new JSObject();
            ret.put("files", files);

            call.resolve(ret);
        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
            call.reject("canceled", "1");
        }
    }

    private static JSObject getCopyFilePath(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        if (!cursor.moveToFirst()) {
            return null;
        }
        String name = cursor.getString(nameIndex);
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1024 * 1024;
            int bufferSize = Math.min(inputStream.available(), maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }
        JSObject result = new JSObject();
        result.put("path", "_capacitor_file_" + file.getPath());
        result.put("name", name);
        result.put("extension", name.substring(name.indexOf('.')));
        return result;
    }
}
