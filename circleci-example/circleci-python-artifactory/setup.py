try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup


setup(
    description="An example of how to create and publish to pypi.org",
    author="Jainish Shah",
    author_email="jainishs@jfrog.com",
    url="https://gcartifactory-us.jfrog.info/artifactory/api/pypi/circleci-pypi/simple",
    version="0.1",
    install_requires=["nose",],
    packages=["pythonProj","tests",],
    name="pythonProj"
)
