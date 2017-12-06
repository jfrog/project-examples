try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup


setup(
    description="An example of how to create and publish to pypi.org",
    author="Ariel Kabov",
    author_email="arielk@jfrog.com",
    url="$ARTIFACTORY_PYTHON_REPOSITORY/simple",
    version="0.1",
    install_requires=["nose",],
    packages=["pythonProj","tests",],
    name="pythonProj"
)
