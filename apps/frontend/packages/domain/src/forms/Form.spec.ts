import { describe, beforeEach, it, expect } from 'vitest';
import Form from './Form.ts';
import FormId from './FormId.ts';
import HexColor from './HexColor.ts';

describe('Form', () => {
  let form: Form;
  const id: string = crypto.randomUUID();
  const organizationId: string = crypto.randomUUID();

  beforeEach(() => {
    form = Form.fromPrimitives({
      id: id,
      name: 'Form',
      header: 'Form header',
      description: 'Form description',
      inputPlaceholder: 'Input placeholder',
      buttonText: 'Submit',
      buttonColor: '#000000',
      backgroundColor: '#FFFFFF',
      textColor: '#000000',
      buttonTextColor: '#FFFFFF',
      organizationId
    });
  });

  it('creates form from primitives successfully', () => {
    expect(form.id).toBeInstanceOf(FormId);
    expect(form.name).toEqual('Form');
    expect(form.header).toEqual('Form header');
    expect(form.description).toEqual('Form description');
    expect(form.inputPlaceholder).toEqual('Input placeholder');
    expect(form.buttonText).toEqual('Submit');
    expect(form.buttonColor).toBeInstanceOf(HexColor);
    expect(form.backgroundColor).toBeInstanceOf(HexColor);
    expect(form.textColor).toBeInstanceOf(HexColor);
    expect(form.buttonTextColor).toBeInstanceOf(HexColor);
  });

  it('converts form to primitives successfully', () => {
    const primitives = form.toPrimitives();

    expect(primitives.id).toEqual(id);
    expect(primitives.name).toEqual('Form');
    expect(primitives.header).toEqual('Form header');
    expect(primitives.description).toEqual('Form description');
    expect(primitives.inputPlaceholder).toEqual('Input placeholder');
    expect(primitives.buttonText).toEqual('Submit');
    expect(primitives.buttonColor).toEqual('#000000');
    expect(primitives.backgroundColor).toEqual('#FFFFFF');
    expect(primitives.textColor).toEqual('#000000');
    expect(primitives.buttonTextColor).toEqual('#FFFFFF');
  });
});
