import { describe, it, expect } from 'vitest';
import FormId from './FormId';
import { InvalidArgumentError } from '@lyra/shared';

describe('FormId', () => {
  it('creates FormId from valid UUID', () => {
    const uuid = '123e4567-e89b-12d3-a456-426614174000';
    const formId = FormId.create(uuid);
    expect(formId.value).toEqual(uuid);
  });

  it('throws error when creating FormId from invalid UUID', () => {
    const invalidUuid = 'invalid-uuid';
    expect(() => FormId.create(invalidUuid)).toThrow(InvalidArgumentError);
  });

  it('creates random FormId successfully', () => {
    const formId = FormId.random();
    expect(formId.value).toMatch(
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/gi,
    );
  });
});
