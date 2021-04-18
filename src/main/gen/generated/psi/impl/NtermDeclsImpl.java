// This is a generated file. Not intended for manual editing.
package generated.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import generated.psi.NtermDecls;
import generated.psi.TokenDecls;
import generated.psi.Visitor;
import org.jetbrains.annotations.NotNull;

public class NtermDeclsImpl extends ASTWrapperPsiElement implements NtermDecls {

  public NtermDeclsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitNtermDecls(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public TokenDecls getTokenDecls() {
    return findNotNullChildByClass(TokenDecls.class);
  }

}
