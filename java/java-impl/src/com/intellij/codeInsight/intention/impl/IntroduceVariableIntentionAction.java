/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.codeInsight.intention.impl;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.introduceVariable.IntroduceVariableHandler;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Danila Ponomarenko
 */
public class IntroduceVariableIntentionAction extends BaseRefactoringAction {
  @NotNull
  @Override
  public String getText() {
    return CodeInsightBundle.message("intention.introduce.variable.text");
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
  }

  @Override
  protected boolean isAvailableOverride(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
    final PsiExpressionStatement statement = PsiTreeUtil.getParentOfType(element,PsiExpressionStatement.class);
    if (statement == null){
      return false;
    }

    final PsiExpression expression = statement.getExpression();

    return expression.getType() != PsiType.VOID && !(expression instanceof PsiAssignmentExpression);
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    final PsiExpressionStatement statement = PsiTreeUtil.getParentOfType(element,PsiExpressionStatement.class);
    if (statement == null){
      return;
    }

    new IntroduceVariableHandler().invoke(project, editor, statement.getExpression());
  }
}
