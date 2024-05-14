import StringValueObject from '../StringValueObject';

describe('StringValueObject', () => {
  class TestValueObject extends StringValueObject {
    static create(value: string): TestValueObject {
      return new TestValueObject(value);
    }
  }

  it('should create a StringValueObject with a valid value', () => {
    const valueObject = TestValueObject.create('test');
    expect(valueObject.value).toBe('test');
  });

  it('should return true when comparing two StringValueObjects with the same value', () => {
    const valueObject1 = TestValueObject.create('test');
    const valueObject2 = TestValueObject.create('test');
    expect(valueObject1.equals(valueObject2)).toBe(true);
  });

  it('should return false when comparing two StringValueObjects with different values', () => {
    const valueObject1 = TestValueObject.create('test1');
    const valueObject2 = TestValueObject.create('test2');
    expect(valueObject1.equals(valueObject2)).toBe(false);
  });

  it('should return the string representation of the value when calling toString', () => {
    const valueObject = TestValueObject.create('test');
    expect(valueObject.toString()).toBe('test');
  });
});
