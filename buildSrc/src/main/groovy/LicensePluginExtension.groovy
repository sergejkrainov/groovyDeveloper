import org.gradle.api.provider.Property

interface LicensePluginExtension {
    Property<String> getPattern()
}