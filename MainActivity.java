package br.supernovaplay.modlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final int PICK_FILE = 101;
    private LinearLayout list;
    private final List<String> imported = new ArrayList<>();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(0xFF111126);
        getWindow().setNavigationBarColor(0xFF111126);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(16));
        root.setBackgroundColor(0xFF111126);

        TextView title = new TextView(this);
        title.setText("SUPERNOVA  •  MOD LAUNCHER");
        title.setTextColor(0xFF8EEBFF);
        title.setTextSize(12);
        title.setTypeface(null, Typeface.BOLD);
        root.addView(title);

        TextView heading = new TextView(this);
        heading.setText("Among Us\nMod Launcher");
        heading.setTextColor(0xFFFFFFFF);
        heading.setTextSize(30);
        heading.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1, -2);
        hp.topMargin = dp(12);
        root.addView(heading, hp);

        TextView desc = new TextView(this);
        desc.setText("Protótipo Android • versão 0.1.0\nImporte e organize seus arquivos de mods.");
        desc.setTextColor(0xFFB8B8D2);
        desc.setTextSize(14);
        LinearLayout.LayoutParams dp0 = new LinearLayout.LayoutParams(-1, -2);
        dp0.topMargin = dp(8);
        root.addView(desc, dp0);

        Button importButton = new Button(this);
        importButton.setText("＋  Importar arquivo de mod");
        importButton.setTextAllCaps(false);
        importButton.setOnClickListener(v -> chooseFile());
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2);
        bp.topMargin = dp(22);
        root.addView(importButton, bp);

        TextView note = new TextView(this);
        note.setText("IMPORTANTE: importar não instala nem ativa o mod no jogo.");
        note.setTextColor(0xFFFFD180);
        note.setTextSize(12);
        LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(-1, -2);
        np.topMargin = dp(8);
        root.addView(note, np);

        TextView section = new TextView(this);
        section.setText("ARQUIVOS IMPORTADOS");
        section.setTextColor(0xFF8EEBFF);
        section.setTextSize(12);
        section.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.topMargin = dp(26);
        root.addView(section, sp);

        ScrollView scroll = new ScrollView(this);
        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(list);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, 0, 1);
        lp.topMargin = dp(10);
        root.addView(scroll, lp);

        TextView footer = new TextView(this);
        footer.setText("Compatibilidade depende do jogo, versão e formato do mod.");
        footer.setTextColor(0xFF777791);
        footer.setTextSize(11);
        footer.setGravity(Gravity.CENTER);
        root.addView(footer);

        setContentView(root);
        renderList();
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri == null) return;
            String name = uri.getLastPathSegment();
            if (name == null || name.trim().isEmpty()) name = uri.toString();
            imported.add(0, name + "\n" + uri.toString());
            renderList();
            Toast.makeText(this, "Arquivo adicionado à lista. Ainda não foi instalado.", Toast.LENGTH_LONG).show();
        }
    }

    private void renderList() {
        list.removeAllViews();
        if (imported.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("Nenhum arquivo importado ainda.\nToque no botão acima para selecionar um arquivo.");
            empty.setTextColor(0xFFB8B8D2);
            empty.setTextSize(14);
            empty.setPadding(dp(14), dp(18), dp(14), dp(18));
            list.addView(empty);
            return;
        }
        for (String item : imported) {
            String[] parts = item.split("\\n", 2);
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(14), dp(12), dp(14), dp(12));
            card.setBackgroundColor(0xFF20203A);
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
            cp.bottomMargin = dp(8);
            TextView name = new TextView(this);
            name.setText(parts[0]);
            name.setTextColor(0xFFFFFFFF);
            name.setTextSize(14);
            name.setTypeface(null, Typeface.BOLD);
            card.addView(name);
            TextView status = new TextView(this);
            status.setText("Importado • compatibilidade não verificada");
            status.setTextColor(0xFFB8B8D2);
            status.setTextSize(11);
            LinearLayout.LayoutParams stp = new LinearLayout.LayoutParams(-1, -2);
            stp.topMargin = dp(5);
            card.addView(status, stp);
            Button open = new Button(this);
            open.setText("Abrir arquivo");
            open.setTextAllCaps(false);
            open.setOnClickListener(v -> {
                try {
                    Intent view = new Intent(Intent.ACTION_VIEW);
                    view.setData(Uri.parse(parts.length > 1 ? parts[1] : ""));
                    view.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(view);
                } catch (Exception e) {
                    Toast.makeText(this, "Nenhum app compatível para abrir este arquivo.", Toast.LENGTH_SHORT).show();
                }
            });
            card.addView(open);
            list.addView(card, cp);
        }
    }

    private int dp(int value) {
        return (int)(value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
