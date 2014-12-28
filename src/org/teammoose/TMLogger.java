/*
 * Author: 598Johnn897
 * 
 * Date: Dec 27, 2014
 * Package: org.teammoose
 */
package org.teammoose;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * <i>Logger source from Java and Bukkit API</i>
 * 
 * @author 598Johnn897
 * @since 0.0.1-SNAPSHOT
 * @see Logger
 */
public class TMLogger extends Logger
{
    private String pluginName;

    /**
     * <i>Source from Bukkit API</i>
     * 
     * @author 598Johnn897
     * @param context A reference to the plugin
     * @since 0.0.1-SNAPSHOT
     * @see Logger
     */
    public TMLogger(Plugin context) {
        super(context.getClass().getCanonicalName(), null);
        String prefix = context.getDescription().getPrefix();
        pluginName = 
        		prefix != null 
        		? new StringBuilder().append("[").append(prefix).append("] ").toString() 
        				: "[" + context.getDescription().getName() + "] ";
        setParent(context.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(pluginName + logRecord.getMessage());
        super.log(logRecord);
    }
}
