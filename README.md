gvSIG 2.0 Developmnet Training excercises
===================================================

My code and notes from the gvSIG 2.0 development course by the gvSIG 
Association.

Have a nice day!

[Jorge](http://jorgesanz.net)


## Day 1

### Lesson 1

- Reviewed docs, nothing to note is a general introduction to the gvSIG 
  architecture.

### Lesson 2

- I start reading the lesson 2
- Change encoding of the workspace to ISO-8851
- I install gvSIG 2.0 dev version (build 2048)
- I have to change to metacity in order to be able to see the 
  _Create Plugin_ interface
- I can progress normally with the rest of the lesson to set up Eclipse
  and review the initial layout of components

#### Notes 

- I had to add an _export_ to the last line of the 
  `.gvisg.platform.properties`
- I had to execute `metacity --replace` to remove compiz so I can see 
  the _Create Plugin_ GUI
- The links on the documents refer to _https_ links on gvSIG portal.
  They should link to the http version
- Is needed the official gvSIG header for this course? Is a good point
  for official developments but for a
  general development is not needed.
- On the _External tools_ favorite section `mvn create_installer` is
  recommended but afterwards it doesn't appear at the screenshot

### Lesson 3

- I read the (great) documentation
- I create the asked interfaces and modify the `VisitorManager`
- I remove the `VisitorService` and associated tests
- I create the github repo so I can track changes on the files easily.
  In fact I should do this first, so the changes on this first coding
  lesson are not tracked but anyways they are small and easy to locate.

### Lesson 4

- Read the documentation
- Implement the asked methods on the tests (both API and Implementation)
- Add the dependencies on the impl `pom.xml`
- Cant't run the tests, I don't know were the tests are, tomorrow I'll
  ask Chema about them and try to finish the exercise in time for the 
  deadline.
- Do the lesson test: 9.4, not bad

## Day 2

### Lesson 4

- Download the data so I can run the lesson tests
- Run the tests, I get an error so I have to debug to find that I had 
  an error on the raster path
- Next error was the name of `Gdal Store`. It's weird having to use
  strings to load a store, there should be a way to find correct stores
  or at least to list those keys. The exception about not finding the 
  store should be a good candidate to show available stores.
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

## Day 3


### Lesson 4

- Start adding comments to API classes and revise 
  the asserts as requested by the teacher

### Lesson 5

- Read the lesson, quite a lot of stuff to do, that's great!!
- No real coding job done today

## Day 4

### Lesson 5

- Coded folowwing the lesson materials, almost without any problem
- The test specification was not understood on the first read but after
  doing the rest of the coding the test was clearer and easier to do
- The test ask for `COORDX` and `COORDY` instead of `COORX` and `COORY`
- Added normal comments but no javadocs as almost all code was on the
  implementation, not on the API side.


## Day 5

## Lesson 6

<div style="float:right;margin:10px 0 10px 10px;">
  <a href="/jsanz/gvsig-2-dev-course/raw/master/imgs/simple-app.png">
    <img style="width:500px;" src="/jsanz/gvsig-2-dev-course/raw/master/imgs/simple-app.png">
  </a>
</div>

- Coded following the lesson materials without major problems, no 
  debug at all
- The HTML from `getText()` was not well formed but it worked
- The rest of the time was spent on improving the panel for the blocks with

  - Formatting area and perimeter numbers
  - Formatting the date properly
  - Addindg old HTML tags for bold fonts
  - Defining a default sensible size
  - Setting an specific font for better bold texts looking
  - Addind an empty border so the elements are not so close to the border

- I would use a gridbag layout but, meh, the border layout is cool
- As always, coding a reasonable good GUI takes more time than any other
  component on a Java app

