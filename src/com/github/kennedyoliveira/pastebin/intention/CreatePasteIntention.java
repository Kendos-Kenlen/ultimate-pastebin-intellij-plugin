package com.github.kennedyoliveira.pastebin.intention;

import com.github.kennedyoliveira.pastebin.UltimatePasteBinIcons;
import com.github.kennedyoliveira.pastebin.ui.forms.CreatePasteForm;
import com.github.kennedyoliveira.pastebin4j.Paste;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.IconUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static com.github.kennedyoliveira.pastebin.i18n.MessageBundle.getMessage;

/**
 * @author kennedy
 */
public class CreatePasteIntention implements IntentionAction, Iconable {

    @Nls
    @NotNull
    @Override
    public String getText() {
        return getMessage("ultimatepastebin.intentions.createpaste.text");
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return getMessage("ultimatepastebin.intentions.createpaste.family");
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        Paste paste = new Paste();

        if (editor.getSelectionModel().getSelectedText() != null) {
            paste.setContent(editor.getSelectionModel().getSelectedText());
        } else {
            paste.setContent(editor.getDocument().getText());
        }

        FileType fileType = PlainTextFileType.INSTANCE;


        if (editor instanceof EditorImpl) {
            VirtualFile virtualFile = ((EditorImpl) editor).getVirtualFile();
            fileType = virtualFile.getFileType();
        }

        CreatePasteForm.createAndShowForm(paste, project, fileType);
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    public Icon getIcon(@IconFlags int flags) {
        return UltimatePasteBinIcons.NEW_PASTE_ICON;
    }
}
