// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package info.fluffos;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import generated.psi.*;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class BisonFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @NotNull
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        ArrayList<FoldingDescriptor> descriptors = Lists.newArrayList();

        // Get a collection of the Rules in the document below root
        Collection<RulesOrGrammarDeclaration> RulesOrgGrammarCollection =
                PsiTreeUtil.findChildrenOfType(root, RulesOrGrammarDeclaration.class);


        var el = root.getFirstChild();
        while((el = PsiTreeUtil.skipWhitespacesAndCommentsForward(el)) != null) {
            if (el instanceof  RulesOrGrammarDeclaration) {
                var child = el.getFirstChild();
                if (child instanceof Rules) {
                    // Add a folding descriptor for the literal expression at this node.
                    descriptors.add(new FoldingDescriptor(child.getNode(),
                            child.getTextRange(),
                            FoldingGroup.newGroup(((Rules)child).getIdColon().getText())));
                }
            }
        }

        for(var e: PsiTreeUtil.findChildrenOfAnyType(root, BracedCode.class, Prologue.class, Epilogue.class)) {
            descriptors.add(new FoldingDescriptor(e.getNode(),
                    e.getTextRange(),
                    FoldingGroup.newGroup("")));
        }

        return descriptors.toArray(new FoldingDescriptor[0]);
    }


    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        var el = node.getPsi();

        if(el instanceof BracedCode) {
            return "{...}";
        } else if (el instanceof Prologue) {
            return "Prologue: ...";
        } else if (el instanceof Epilogue) {
            return "Epilogue: ...";
        } else if (el instanceof Rules) {
            return "Rule: ...";
        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }

}