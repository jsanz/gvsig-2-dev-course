Day 2
============================================

## Lesson 4

- Download the data so I can run the lesson tests
- Run the tests, I get an error so I have to debug to find that I had an error on the raster path
- Next error was the name of `Gdal Store`. It's weird having to use strings to load a store, there should be a way to find correct stores or at least to list those keys. The exception about not finding the store should be a good candidate to show available stores.
- All green, yay!!
- Trying to run the tests from `maven test` external tools but failed
- Make a `mvn clean` and `mvn compile` from parent project `org.gvsig.visor`
- I still have errors refering to an old `.getVisorService()` method

	-------------------------------------------------------------------------------
	Test set: org.gvsig.visor.impl.DefaultVisorManagerTest
	-------------------------------------------------------------------------------
	Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 10.551 sec <<< FAILURE!
	testGetVisorService(org.gvsig.visor.impl.DefaultVisorManagerTest)  Time elapsed: 10.362 sec  <<< ERROR!
	java.lang.NoSuchMethodError: org.gvsig.visor.VisorManager.getVisorService()Lorg/gvsig/visor/VisorService;
		at org.gvsig.visor.VisorManagerTest.testGetVisorService(VisorManagerTest.java:52)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)

- I'll ask on the forum and upload a clean clone of the repo


## Commits

- [Lesson 4 (2)](https://github.com/jsanz/gvsig-2-dev-course/commit/158d6cfbba07a9779d908cf8d76b32c941c95569) 
