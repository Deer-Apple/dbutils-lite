# dbutils-lite

#### Overview
A super tiny lite version of Apache Commons DbUtils. The goal is to improve concept understanding and practice coding skills.

### How it works
- dbutils-lite doesn't handle the Database connections, it just helps to directly construct the object from query result through `ResultSetHandler`.

- When we get the `ResultSet` from a query, we pass it to the `ResultSetHandler<T>.handle(ResultSet rs)`, here the generic type is what we will return. I've implemented two basic `ResultSetHandler` which is `BeanHandler<T> implements ResultSetHandler<T>` and `BeanListHandler<T> implements ResultSetHandler<List<T>>`.

- In order to set fields in an object with its corresponding value in `ResultSet`, we use `PropertyDescriptor` where we can get the setter method of a field through `getWriteMethod()`. Then we do the `invoke()` with the value we get from `ResultSet`.