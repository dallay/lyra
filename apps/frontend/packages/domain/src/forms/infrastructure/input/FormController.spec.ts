import 'reflect-metadata';
import { vi } from 'vitest';
import { mock, type MockProxy } from 'vitest-mock-extended';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import FormController from '~/forms/infrastructure/input/FormController.ts';
import FormFinder from '~/forms/application/find/FormFinder.ts';
import FormsSearcher from '~/forms/application/search/FormsSearcher.ts';
import FormUpdater from '~/forms/application/update/FormUpdater.ts';
import FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import FormCreator from '~/forms/application/create/FormCreator.ts';
import CreateFormRequest from '~/forms/infrastructure/input/request/CreateFormRequest.ts';
import UpdateFormRequest from '~/forms/infrastructure/input/request/UpdateFormRequest.ts';
import Form from '~/forms/domain/Form.ts';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import type { PageResponse } from '~/types/types.ts';
import FormResponse from '~/forms/domain/FormResponse.ts';

describe('FormController', () => {
  const id = '3021a8b5-9a18-4b44-9177-3c0fd1809fd1';
  const formDestroyerRepository: MockProxy<FormDestroyerRepository> =
    mock<FormDestroyerRepository>();
  const formRepository: MockProxy<FormRepository> = mock<FormRepository>();
  const formFinderRepository: MockProxy<FormFinderRepository> = mock<FormFinderRepository>();
  let formController: FormController;
  let formFinder: FormFinder;
  let formsSearcher: FormsSearcher;
  let formUpdater: FormUpdater;
  let formDestroyer: FormDestroyer;
  let formCreator: FormCreator;
  let form: Form;

  beforeEach(() => {
    vi.useFakeTimers({
      now: new Date('2022-01-01T00:00:00Z'),
    });
    form = Form.fromPrimitives({
      id: id,
      name: 'Form',
      header: 'Form header',
      description: 'Form description',
      inputPlaceholder: 'Input placeholder',
      buttonText: 'Submit',
      buttonColor: '#000000',
      backgroundColor: '#000000',
      textColor: '#FFFFFF',
      buttonTextColor: '#FFFFFF',
      organizationId: '9083a65f-86d8-4d27-8473-69f12d665c42',
    });
    formFinder = new FormFinder(formFinderRepository);
    formsSearcher = new FormsSearcher(formFinderRepository);
    formUpdater = new FormUpdater(formRepository);
    formDestroyer = new FormDestroyer(formDestroyerRepository);
    formCreator = new FormCreator(formRepository);
    formController = new FormController(
      formFinder,
      formsSearcher,
      formUpdater,
      formDestroyer,
      formCreator,
    );
  });

  it('creates form successfully', async () => {
    const request = new CreateFormRequest(
      form.id.value,
      form.name,
      form.header,
      form.description,
      form.inputPlaceholder,
      form.buttonText,
      form.buttonColor.value,
      form.backgroundColor.value,
      form.textColor.value,
      form.buttonTextColor.value,
      form.organizationId,
    );
    formRepository.create.mockResolvedValue();

    await formController.create(request);

    expect(formRepository.create).toHaveBeenCalledWith(Form.fromPrimitives(request));
  });

  it('finds all forms successfully', async () => {
    const formResponse = FormResponse.from(form);
    const pageResponse: PageResponse<FormResponse> = {
      data: [formResponse],
      nextPageCursor: '',
    };
    formFinderRepository.search.mockReturnValue(Promise.resolve(pageResponse));

    const response = await formController.findAll();

    expect(formFinderRepository.search).toHaveBeenCalledWith(undefined, undefined, 10, '');
    expect(response).toEqual(pageResponse);
  });

  it('finds form by id successfully', async () => {
    const formResponse = FormResponse.from(form);
    formFinderRepository.find.mockReturnValue(Promise.resolve(formResponse));

    const response = await formController.find(id);

    expect(formFinderRepository.find).toHaveBeenCalledWith(id);
    expect(response).toBeInstanceOf(FormResponse);
  });

  it('updates form successfully', async () => {
    const request = new UpdateFormRequest(
      form.name,
      form.header,
      form.description,
      form.inputPlaceholder,
      form.buttonText,
      form.buttonColor.value,
      form.backgroundColor.value,
      form.textColor.value,
      form.buttonTextColor.value,
      form.organizationId,
    );
    formRepository.update.mockResolvedValue();

    await formController.update(id, request);

    expect(formRepository.update).toHaveBeenCalledWith(Form.fromPrimitives({ ...request, id: id }));
  });

  it('deletes form successfully', async () => {
    formDestroyerRepository.delete.mockResolvedValue();

    await formController.delete(id);

    expect(formDestroyerRepository.delete).toHaveBeenCalledWith(id);
  });
});
