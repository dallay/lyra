import 'reflect-metadata';
import { Container } from 'inversify';
import { formsModule } from '@lyra/api-services';

const dependenciesContainer = new Container();

const setupApplicationDependencies = async (): Promise<void> => {
	dependenciesContainer.load(formsModule);
};

setupApplicationDependencies().then((r) => r);

export default dependenciesContainer;
