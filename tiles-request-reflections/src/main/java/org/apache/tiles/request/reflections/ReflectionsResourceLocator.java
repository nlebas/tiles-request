package org.apache.tiles.request.reflections;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.tiles.request.ApplicationResource;
import org.apache.tiles.request.locale.AbstractURLBasedResourceLocator;
import org.apache.tiles.request.locale.URLApplicationResource;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ReflectionsResourceLocator extends AbstractURLBasedResourceLocator {

	private Reflections reflections;

	public ReflectionsResourceLocator() {
		this(ClasspathHelper.forClassLoader());
	}

	public ReflectionsResourceLocator(Collection<URL> urls) {
		this.reflections = new Reflections(new ConfigurationBuilder().addUrls(
				urls).setScanners(new ResourcesScanner()));
	}

	@Override
	public Collection<ApplicationResource> getResources(String path) {
		ArrayList<ApplicationResource> result = new ArrayList<ApplicationResource>();
		for (String resourcePath : reflections.getStore()
				.get(ResourcesScanner.class.getSimpleName()).keySet()) {
			URLApplicationResource resource = getResource(resourcePath);
			if (resource != null) {
				result.add(resource);
			}
		}
		return Collections.unmodifiableCollection(result);
	}

	@Override
	public URLApplicationResource getResource(String localePath) {
		Iterator<String> iterator = reflections.getStore().get(ResourcesScanner.class.getSimpleName(), localePath).iterator();
		if(iterator.hasNext()) {
			URLApplicationResource result;
			try {
				result = new URLApplicationResource(localePath, new URL(iterator.next()));
			} catch (MalformedURLException e) {
				return null;
			}
			if(iterator.hasNext()) {
				throw new IllegalStateException("multiple resources found for "+localePath);
			}
			return result;
		}
		else {
			return null;
		}
	}
}
