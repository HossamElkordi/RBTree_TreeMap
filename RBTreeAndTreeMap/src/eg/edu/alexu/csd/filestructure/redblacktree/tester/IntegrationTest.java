package eg.edu.alexu.csd.filestructure.redblacktree.tester;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class IntegrationTest {
   private final Class<?> redBlackTreeInterfaceToTest = IRedBlackTree.class;
   private final Class<?> treeMapInterfaceToTest = ITreeMap.class;

   @Test
   public void testCreationRedBlackTree() {
      List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(this.redBlackTreeInterfaceToTest, this.redBlackTreeInterfaceToTest.getPackage());
      Assert.assertNotNull("Failed to create instance using interface '" + this.redBlackTreeInterfaceToTest.getName() + "' !", candidateClasses);
      Assert.assertEquals("You have more than one public implementation of the interface", 1L, (long)candidateClasses.size());
   }

   @Test
   public void testCreationTreeMap() {
      List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(this.treeMapInterfaceToTest, this.treeMapInterfaceToTest.getPackage());
      Assert.assertNotNull("Failed to create instance using interface '" + this.treeMapInterfaceToTest.getName() + "' !", candidateClasses);
      Assert.assertEquals("You have more than one public implementation of the interface", 1L, (long)candidateClasses.size());
   }
}
