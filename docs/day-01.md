Day 1
============================================


# Lesson 1

- Reviewed docs, nothing to note is a general introduction to the gvSIG architecture.


# Lesson 2

- I start reading the lesson 2
- Change encoding of the workspace to ISO-8851
- I install gvSIG 2.0 dev version (build 2048)
- I have to change to metacity in order to be able to see the _Create Plugin_ interface
- I can progress normally with the rest of the lesson to set up Eclipse and review the initial layout of components

## Notes 

- I had to add an _export_ to the last line of the `.gvisg.platform.properties`
- I had to execute `metacity --replace` to remove compiz so I can see the _Create Plugin_ GUI
- The links on the documents refer to _https_ links on gvSIG portal. They should link to the http version
- Is needed the official gvSIG header for this course? Is a good point for official developments but for a
  general development is not needed.
- On the _External tools_ favorite section `mvn create_installer` is recommended but afterwards it doesn't appear
  at the screenshot

# Lesson 3

- I read the (great) documentation
- I create the asked interfaces and modify the `VisitorManager`
- I remove the `VisitorService` and associated tests
- I create the github repo so I can track changes on the files easily. In fact I should do this first, so the changes
  on this first coding lesson are not tracked but anyways they are small and easy to locate.


# Commits

- [Lesson 3 commit](https://github.com/jsanz/gvsig-2-dev-course/commit/f4614a8d897c162fa423f93e74de697f50a6e690) with 
  all the generated code from the fortune cookie sample plugin plus the minor interface coding from lesson 3.


