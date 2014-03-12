package ut.org.networkedassets.atlassian.stash.private_repositories_permissions;

import org.junit.Test;
import org.networkedassets.atlassian.stash.private_repositories_permissions.MyPluginComponent;
import org.networkedassets.atlassian.stash.private_repositories_permissions.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}