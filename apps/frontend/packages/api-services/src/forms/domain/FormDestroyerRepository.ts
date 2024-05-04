export const FORM_DESTROYER_REPOSITORY_PROVIDER = 'FORM_DESTROYER_REPOSITORY_PROVIDER';

export default interface FormDestroyerRepository {
	delete(id: string): Promise<void>;
}
