package be.simulation.core.configuration;

public class GenericConfigurationFactory<T extends AbstractConfiguration>
		implements ConfigurationFactory<T> {

	public T create(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
