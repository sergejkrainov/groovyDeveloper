import org.gradle.api.provider.Property

interface GenerateSourcePluginExtension {
    Property<String> getClassName()
    Property<String> getFieldName()
    Property<String> getFieldValue()
    Property<String> getPackageName()
    Property<String> getRootPath()

}