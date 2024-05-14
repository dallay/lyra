import ValueObject from '../ValueObject';

describe('ValueObject', () => {
  class TestValueObject extends ValueObject<string> {
    constructor(value: string) {
      super(value);
    }
  }

  it('should create a ValueObject with a valid value', () => {
    const valueObject = new TestValueObject('test');
    expect(valueObject.value).toBe('test');
  });

  it('should return true when comparing two ValueObjects with the same value', () => {
    const valueObject1 = new TestValueObject('test');
    const valueObject2 = new TestValueObject('test');
    expect(valueObject1.equals(valueObject2)).toBe(true);
  });

  it('should return false when comparing two ValueObjects with different values', () => {
    const valueObject1 = new TestValueObject('test1');
    const valueObject2 = new TestValueObject('test2');
    expect(valueObject1.equals(valueObject2)).toBe(false);
  });

  it('should return the string representation of the value when calling toString', () => {
    const valueObject = new TestValueObject('test');
    expect(valueObject.toString()).toBe('test');
  });
});
