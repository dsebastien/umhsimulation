package be.simulation.core.configuration;

public interface ConfigurationFactory<T extends AbstractConfiguration> {
	public T create(Class<T> clazz);
}
