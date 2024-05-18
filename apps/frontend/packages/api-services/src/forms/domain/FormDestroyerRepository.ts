export default interface FormDestroyerRepository {
	delete(id: string): Promise<void>;
}
