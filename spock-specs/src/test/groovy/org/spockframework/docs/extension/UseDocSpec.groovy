package org.spockframework.docs.extension

import groovy.transform.CompileDynamic
import spock.lang.Specification
import spock.lang.SpockCompileDynamic
import spock.util.mop.Use

@CompileDynamic
// tag::example[]
class ListExtensions {
  static avg(List list) { list.sum() / list.size() }
}

// end::example[]
@SpockCompileDynamic
// tag::example[]
class UseDocSpec extends Specification {
  @Use(ListExtensions)
  def "can use avg() method"() {
    expect:
    [1, 2, 3].avg() == 2
  }
}
// end::example[]
