package org.openzal.zal.lucene.search;

import org.jetbrains.annotations.NotNull;
import org.openzal.zal.lucene.document.DocumentId;

public class ScoreDoc
{
  /* $if ZimbraVersion >= 8.5.0 $ */
  private com.zimbra.cs.index.ZimbraScoreDoc mZObject;
  /* $endif $ */

  public ScoreDoc(@NotNull Object zObject)
  {
    /* $if ZimbraVersion >= 8.5.0 $ */
    mZObject = (com.zimbra.cs.index.ZimbraScoreDoc) zObject;
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  public DocumentId getDocumentId()
  {
    /* $if ZimbraVersion >= 8.5.0 $ */
    return new DocumentId(mZObject.getDocumentID());
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  public float getScore()
  {
    /* $if ZimbraVersion >= 8.5.0 $ */
    return mZObject.getScore();
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  @Override
  public String toString()
  {
    /* $if ZimbraVersion >= 8.5.0 $ */
    return mZObject.toString();
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }

  public <T> T toZimbra(@NotNull Class<T> target)
  {
    /* $if ZimbraVersion >= 8.5.0 $ */
    return target.cast(mZObject);
    /* $else $
    throw new UnsupportedOperationException();
    /* $endif $ */
  }
}